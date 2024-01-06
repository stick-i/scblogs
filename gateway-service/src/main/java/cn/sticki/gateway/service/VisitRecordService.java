package cn.sticki.gateway.service;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.sticki.common.web.utils.RequestUtils;
import cn.sticki.gateway.filter.AuthorizeFilter;
import cn.sticki.gateway.pojo.VisitRecord;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 访问服务
 *
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/17 19:49
 */
@Slf4j
@Service
public class VisitRecordService {

	private final String attributeKey = "visitRecord";

	public VisitRecordService() {
		this.shutdownHook();
	}

	private void shutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			this.batchSave();
			threadPool.shutdown();
		}));
	}

	@Nullable
	public VisitRecord get(ServerWebExchange exchange) {
		return exchange.getAttribute(attributeKey);
	}

	/**
	 * 从 ServerWebExchange 中获取当前访问信息
	 * <p>
	 * 如果没有，则构建一个新的对象存入exchange中
	 *
	 * @param exchange gateway访问合同
	 * @return 访问信息，可能为空（当请求未经过{@link cn.sticki.gateway.filter.VisitRecordFilter}处理时）
	 */
	public VisitRecord getOrBuild(ServerWebExchange exchange) {
		VisitRecord visitRecord = get(exchange);
		if (visitRecord == null) {
			visitRecord = build(exchange);
			put(exchange, visitRecord);
		}
		return visitRecord;
	}

	/**
	 * 构建一个 VisitRecord 实体类，但仅适用于获取 request 信息
	 *
	 * @param exchange gateway访问合同
	 * @return 访问信息
	 */
	public VisitRecord build(ServerWebExchange exchange) {
		// 获取请求信息
		ServerHttpRequest request = exchange.getRequest();
		String ip = RequestUtils.getIpAddress(request);

		// 构造访问记录实体类
		VisitRecord visitRecord = new VisitRecord();
		visitRecord.setIp(ip);
		visitRecord.setMethod(String.valueOf(request.getMethod()));
		visitRecord.setUri(request.getURI().getPath());
		visitRecord.setQueryParam(request.getURI().getQuery());
		visitRecord.setCreatTime(LocalDateTime.now());

		return visitRecord;
	}

	/**
	 * 将访问信息存入 ServerWebExchange 当中，将会与当前请求关联起来，
	 * 以便于后续在任何地方均可获得
	 *
	 * @param exchange    gateway访问合同
	 * @param visitRecord 访问信息
	 */
	public void put(ServerWebExchange exchange, VisitRecord visitRecord) {
		Map<String, Object> attributes = exchange.getAttributes();
		attributes.put(attributeKey, visitRecord);
	}

	/**
	 * 保存访问记录
	 *
	 * @param exchange gateway访问合同
	 */
	public void add(ServerWebExchange exchange) {
		// 获取数据
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		VisitRecord visitRecord = getOrBuild(exchange);

		// 设置访问状态和用户id（如果有）
		if (response.getStatusCode() != null) {
			visitRecord.setStatus(response.getStatusCode().value());
		}
		visitRecord.setUserId(AuthorizeFilter.getUserId(request));

		// 打印访问情况
		log.info(visitRecord.toString());

		// 添加访问记录
		addRecord(visitRecord);
	}

	/**
	 * 缓存，在插入数据库前先存入此。
	 * 为防止数据被重复插入，故使用Set，但不能确保100%不会被重复存储。
	 */
	private HashSet<VisitRecord> visitCache = new HashSet<>();

	/**
	 * 信号量，用于标记当前是否有任务正在执行，{@code true}表示当前无任务进行。
	 */
	private volatile boolean taskFinish = true;

	private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("visit-record-").build();

	private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 3, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

	private void addRecord(VisitRecord record) {
		// 添加记录到缓存中
		visitCache.add(record);
		// 执行任务，保存数据
		doTask();
	}

	private void doTask() {
		if (taskFinish) {
			// 当前没有任务的情况下，加锁并执行任务
			synchronized (this) {
				if (taskFinish) {
					taskFinish = false;
					threadPool.execute(() -> {
						try {
							// 当数据量较小时，则等待一段时间再插入数据，从而做到将数据尽可能的批量插入数据库
							if (visitCache.size() <= BATCH_SIZE) {
								Thread.sleep(500);
							}
							batchSave();
						} catch (InterruptedException e) {
							log.error("睡眠时发生了异常: {}", e.getMessage());
						} finally {
							// 任务执行完毕后修改标志位
							taskFinish = true;
						}
						// todo 并发情况下，可能出现 (整个任务完成前，hashSet更新后) 插入数据的情况，此时如果无新任务调度，则数据不会被主动保存
					});
				}
			}
		}
	}

	@Resource
	VisitLogService visitLogService;

	/**
	 * 单次批量插入的数据量
	 */
	private final int BATCH_SIZE = 500;

	/**
	 * 缩减因子，每次更新缓存Set时缩小的倍数，对应HashSet的扩容倍数
	 */
	private final float REDUCE_FACTOR = 0.5f;

	private void batchSave() {
		log.debug("访问记录准备插入数据库，当前数据量：{}", visitCache.size());
		if (visitCache.size() == 0) {
			return;
		}
		// 构造新对象来存储数据，旧对象保存到数据库后不再使用
		HashSet<VisitRecord> oldCache = visitCache;
		visitCache = new HashSet<>((int) (oldCache.size() * REDUCE_FACTOR));
		if (!visitLogService.saveBatch(oldCache, BATCH_SIZE)) {
			// 第一次保存失败，进行重试
			if (!visitLogService.saveBatch(oldCache, BATCH_SIZE)) {
				log.error("访问日志保存异常，丢弃数据{}条", oldCache.size());
			}
		}
	}

}

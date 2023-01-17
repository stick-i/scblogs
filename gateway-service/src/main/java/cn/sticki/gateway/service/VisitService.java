package cn.sticki.gateway.service;

import cn.sticki.common.web.utils.RequestUtils;
import cn.sticki.gateway.pojo.VisitRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;
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
public class VisitService {

	@Value("${gateway.ip.limit.key:gateway:ipLimit:}")
	private String ipLimitKey;

	@Value("${gateway.ip.limit.count:30}")
	private int ipLimitCount;

	@Value("${gateway.ip.limit.time:5}")
	private long ipLimitTime;

	@Value("${gateway.ip.limit.ttl:10}")
	private long ipLimitTtl;

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	@Resource
	private ObjectMapper objectMapper;

	/**
	 * 检查是否超过访问限制
	 * <p>
	 * todo 有待优化，目前逻辑为，5s内连续访问30次，需要冷却10s
	 *
	 * @param exchange 访问信息
	 * @return 是否超过限制，{@code true}超出限制
	 */
	public boolean checkLimit(ServerWebExchange exchange) {
		// 0. 获取请求信息
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String ip = RequestUtils.getIpAddress(request);

		VisitRecord visitRecord = new VisitRecord();
		visitRecord.setIp(ip);
		visitRecord.setMethod(String.valueOf(request.getMethod()));
		visitRecord.setStatus(String.valueOf(response.getStatusCode()));
		visitRecord.setUri(request.getURI().getPath());
		visitRecord.setQueryParam(request.getURI().getQuery());
		visitRecord.setCreatTime(LocalDateTime.now());

		// 1. 打印访问情况
		log.info(visitRecord.toString());

		// 2. 获取ip计数，缓存中没有则给0
		String key = ipLimitKey + ip;
		int ipCount = Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(0);

		// 3. 超过限制，禁止访问
		if (ipCount > ipLimitCount) {
			// 设置冷却时间
			redisTemplate.expire(key, ipLimitTtl, TimeUnit.SECONDS);
			log.info("ip limit : ip={}, url={}", ip, visitRecord.getUri());
			return true;
		}

		// 4. ip计数写入redis并放行
		redisTemplate.opsForValue().set(key, ipCount + 1, ipLimitTime, TimeUnit.SECONDS);
		return false;
	}

	/**
	 * 构建包装类型，并写入一个Json类型的返回值
	 *
	 * @param response 响应体
	 * @param value    返回值
	 * @return 包装类型
	 */
	public Mono<Void> buildMonoWrap(ServerHttpResponse response, Object value) {
		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			try {
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				return bufferFactory.wrap(objectMapper.writeValueAsBytes(value));
			} catch (JsonProcessingException e) {
				log.error("Error writing response", e);
				return bufferFactory.wrap(new byte[0]);
			}
		}));
	}

}

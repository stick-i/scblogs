package cn.sticki.gateway.filter;

import cn.sticki.common.web.utils.RequestUtils;
import cn.sticki.gateway.pojo.VisitRecord;
import cn.sticki.gateway.service.VisitRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 访问记录过滤器，保存访问记录，最先执行
 *
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/18 16:40
 */
@Slf4j
@Component
public class VisitRecordFilter implements GlobalFilter, Ordered {

	@Resource
	VisitRecordService visitRecordService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 0. 获取请求信息
		ServerHttpRequest request = exchange.getRequest();
		String ip = RequestUtils.getIpAddress(request);

		// 构造访问记录实体类
		VisitRecord visitRecord = new VisitRecord();
		visitRecord.setIp(ip);
		visitRecord.setMethod(String.valueOf(request.getMethod()));
		visitRecord.setUri(request.getURI().getPath());
		visitRecord.setQueryParam(request.getURI().getQuery());
		visitRecord.setCreatTime(LocalDateTime.now());

		visitRecordService.put(exchange, visitRecord);

		return chain.filter(exchange).then(saveRecord(exchange));
	}

	private Mono<Void> saveRecord(ServerWebExchange exchange) {
		return Mono.fromRunnable(() -> visitRecordService.saveRecord(exchange));
	}

	@Override
	public int getOrder() {
		return -10000;
	}

}

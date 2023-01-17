package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.gateway.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * ip访问限制过滤器
 *
 * @author 阿杆
 */
@Slf4j
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

	@Resource
	private VisitService visitService;

	/**
	 * 限制接口频繁访问
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		boolean isLimit = visitService.checkLimit(exchange);

		// 超过访问限制，拒绝访问
		if (isLimit) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.LOCKED);
			return visitService.buildMonoWrap(response, RestResult.limit());
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1000;
	}

}

package cn.sticki.gateway.filter;

import cn.sticki.gateway.config.SecurityConfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 阿杆
 */
@Component
public class PathMatcherFilter implements GlobalFilter, Ordered {

	@SuppressWarnings("AlibabaUndefineMagicConstant")
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getPath().toString();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		// 如果是需要登录的接口，在此进行判断并拦截
		for (String pattern : SecurityConfig.mustAuthUrl.split(",")) {
			if (antPathMatcher.match(pattern, path)) {
				// 匹配成功，判断是否登录
				if (request.getHeaders().getFirst(SecurityConfig.identityHeader) == null) {
					// 没有登录，直接拦截
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();
				}
			}
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1;
	}

}

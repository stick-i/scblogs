package cn.sticki.gateway.filter;

import cn.hutool.jwt.JWT;
import cn.sticki.gateway.config.JwtConfig;
import cn.sticki.gateway.config.SecurityConfig;
import cn.sticki.gateway.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 阿杆
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 通过request中的id来判断用户
		ServerHttpRequest request = exchange.getRequest();
		// id 存在的情况下，认为用户恶意操作，直接拦截。
		// 判断token
		if (request.getHeaders().getFirst(SecurityConfig.identity) != null) {
			// 拦截请求
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		// 获取token
		String token = request.getHeaders().getFirst(JwtConfig.headerName);
		if (StringUtils.hasText(token)) {
			// 验证、解析token
			JWT jwt = JwtUtils.validateAndParse(token);
			Object object = null;
			if (jwt != null) {
				object = jwt.getPayload("id");
			}
			if (object instanceof Integer) {
				Integer id = (Integer) object;
				ServerHttpRequest build = exchange.getRequest().mutate().header("id", id.toString()).build();
				exchange = exchange.mutate().request(build).build();
			}
		}
		// 放行
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -2;
	}

}

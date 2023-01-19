package cn.sticki.gateway.filter;

import cn.hutool.jwt.JWT;
import cn.sticki.common.result.RestResult;
import cn.sticki.gateway.config.JwtConfig;
import cn.sticki.gateway.config.SecurityConfig;
import cn.sticki.gateway.utils.JwtUtils;
import cn.sticki.gateway.utils.MonoUtils;
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
 * 权限认证过滤器
 *
 * @author 阿杆
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 通过request中的id来判断用户
		ServerHttpRequest request = exchange.getRequest();
		// 未经过网关就存在 user-id 的情况下，认为用户恶意操作，直接拦截。
		if (getUserId(request) != null) {
			// 拦截请求
			return MonoUtils.buildMonoWrap(exchange.getResponse(), RestResult.fail(), HttpStatus.BAD_REQUEST);
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
				ServerHttpRequest build = request.mutate().header(SecurityConfig.identityHeader, id.toString()).build();
				exchange = exchange.mutate().request(build).build();
			}
		}
		// 放行
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -100;
	}

	/**
	 * 获取存储在request header上的用户信息，但有可能为空
	 *
	 * @param request 请求体
	 * @return 用户id
	 */
	public static Long getUserId(ServerHttpRequest request) {
		Long res = null;
		String identity = request.getHeaders().getFirst(SecurityConfig.identityHeader);
		if (identity != null) {
			try {
				res = Long.parseLong(identity);
			} catch (NumberFormatException ignored) {
			}
		}
		return res;
	}

}

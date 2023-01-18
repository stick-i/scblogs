package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.gateway.config.SecurityConfig;
import cn.sticki.gateway.utils.MonoUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 访问路径过滤器，处理必须登录的路径和不存在的路径
 *
 * @author 阿杆
 */
@Component
public class PathMatcherFilter implements GlobalFilter, Ordered {

	/**
	 * 这里的值必须和配置文件当中兜底的转发路由id相同，否则不会生效
	 */
	final String NOT_FOUNT = "not-found";

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@SuppressWarnings("AlibabaUndefineMagicConstant")
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 判断当前转发路径是否为not-found
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		assert route != null;
		ServerHttpResponse response = exchange.getResponse();
		if (NOT_FOUNT.equals(route.getId())) {
			return MonoUtils.buildMonoWrap(response, RestResult.notFound(), HttpStatus.NOT_FOUND);
		}

		ServerHttpRequest request = exchange.getRequest();
		String path = request.getPath().toString();

		// 匹配路径，判断是否为需要登录的接口
		boolean isMatch = false;
		for (String pattern : SecurityConfig.mustAuthUrl.split(",")) {
			isMatch = antPathMatcher.match(pattern, path);
			if (isMatch) {
				break;
			}
		}
		// 若路径匹配成功且未登录，则意味着没有权限，直接拦截
		boolean noAuth = isMatch && AuthorizeFilter.getUserId(request) == null;
		if (noAuth) {
			return MonoUtils.buildMonoWrap(response, RestResult.fail("用户未登录"), HttpStatus.UNAUTHORIZED);
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 10;
	}

}

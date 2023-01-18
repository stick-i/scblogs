package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.utils.RequestUtils;
import cn.sticki.gateway.config.IpLimitConfig;
import cn.sticki.gateway.utils.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * ip访问限制过滤器
 *
 * @author 阿杆
 */
@Slf4j
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

	/**
	 * 限制接口频繁访问
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		boolean isLimit = checkLimit(exchange);

		// 超过访问限制，拒绝访问
		if (isLimit) {
			return MonoUtils.buildMonoWrap(exchange.getResponse(), RestResult.limit(), HttpStatus.LOCKED);
		}

		return chain.filter(exchange);
	}

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	/**
	 * 检查是否超过访问限制
	 * <p>
	 * todo 有待优化，目前逻辑为，5s内连续访问30次，需要冷却10s
	 *
	 * @param exchange 访问信息
	 * @return 是否超过限制，{@code true}超出限制
	 */
	public boolean checkLimit(ServerWebExchange exchange) {
		// 1. 获取请求信息
		ServerHttpRequest request = exchange.getRequest();
		String ip = RequestUtils.getIpAddress(request);

		// 2. 获取ip计数，缓存中没有则给0
		String key = IpLimitConfig.key + ip;
		int ipCount = Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(0);

		// 3. 超过限制，禁止访问
		if (ipCount > IpLimitConfig.count) {
			// 设置冷却时间
			redisTemplate.expire(key, IpLimitConfig.ttl, TimeUnit.SECONDS);
			log.info("ip limit : ip={}", ip);
			return true;
		}

		// 4. ip计数写入redis并放行
		redisTemplate.opsForValue().set(key, ipCount + 1, IpLimitConfig.time, TimeUnit.SECONDS);
		return false;
	}

	@Override
	public int getOrder() {
		return -1;
	}

}

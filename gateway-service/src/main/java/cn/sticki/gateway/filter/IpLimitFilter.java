package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static cn.sticki.gateway.utils.RedisConstants.GATEWAY_IPLIMIT_KEY;
import static cn.sticki.gateway.utils.RedisConstants.GATEWAY_IPLIMIT_TTL;

/**
 * ip访问限制过滤器
 *
 * @author 阿杆
 */
@Slf4j
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

	private final static int LIMIT_COUNT = 30;

	private final static int LIMIT_TIME = 10;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	@SneakyThrows(IOException.class)
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		// 0.打印访问情况
		log.info("{} {} {} {}", request.getRemoteAddress(), request.getMethod(), response.getStatusCode(), request.getPath());
		// todo 有待优化，目前逻辑为，5s内连续访问30次，需要冷却10s
		// 1. 获取当前ip
		String ip = RequestUtils.getIpAddress(request);
		// 2. 获取ip计数
		String key = GATEWAY_IPLIMIT_KEY + ip;
		Integer ipCount = redisTemplate.opsForValue().get(key);
		if (ipCount == null) {
			ipCount = 0;
		}
		// 3.判断ip访问次数
		if (ipCount > LIMIT_COUNT) {
			// 3.1 超过限制，禁止访问
			// 3.10 重置当前ip的ttl为限制时长
			redisTemplate.opsForValue().set(key, ipCount, LIMIT_TIME, TimeUnit.SECONDS);
			// 3.11 设置状态码和响应类型
			response.setStatusCode(HttpStatus.LOCKED);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			// 3.12 构造返回体
			DataBufferFactory bufferFactory = response.bufferFactory();
			log.info("ip limit : {} {}", ip, exchange.getRequest().getPath());
			DataBuffer wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(new RestResult<>(408, "访问频繁，请稍后再试")));
			return response.writeWith(Mono.fromSupplier(() -> wrap));
		} else {
			// 3.2 未超过限制，正常访问，访问次数+1
			ipCount++;
			redisTemplate.opsForValue().set(key, ipCount, GATEWAY_IPLIMIT_TTL, TimeUnit.SECONDS);
			// 3.3 放行
			return chain.filter(exchange);
		}
	}

	@Override
	public int getOrder() {
		return -1000;
	}

}
package cn.sticki.gateway.filter;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.utils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
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

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${gateway.ip.limit.key:gateway:ipLimit:}")
	private String gatewayIpLimitKey;

	@Value("${gateway.ip.limit.count:30}")
	private int gatewayIpLimitCount;

	@Value("${gateway.ip.limit.time:5}")
	private long gatewayIpLimitTime;

	@Value("${gateway.ip.limit.time:10}")
	private long gatewayIpLimitTtl;

	@Resource
	private RedisTemplate<String, Integer> redisTemplate;

	/**
	 * 限制接口频繁访问
	 * todo 有待优化，目前逻辑为，5s内连续访问30次，需要冷却10s
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 0. 获取请求，响应，ip与uri
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String ip = RequestUtils.getIpAddress(request);
		RequestPath uri = request.getPath();

		// 1. 打印访问情况
		log.info("ip={}, method={}, status={}, uri={}", ip, request.getMethod(), response.getStatusCode(), uri);

		// 2. 获取ip计数，缓存中没有则给0
		String key = gatewayIpLimitKey + ip;
		int ipCount = Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(0);

		// 3. 超过限制，禁止访问
		if (ipCount > gatewayIpLimitCount) {
			return response.writeWith(Mono.fromSupplier(() -> buildWrap(response, ip, uri, key)));
		}

		// 4. ip计数写入redis并放行
		redisTemplate.opsForValue().set(key, ++ipCount, gatewayIpLimitTime, TimeUnit.SECONDS);
		return chain.filter(exchange);
	}

	@SneakyThrows
	private DataBuffer buildWrap(ServerHttpResponse response, String ip, RequestPath uri, String key){
		// 设置状态码和响应类型
		response.setStatusCode(HttpStatus.LOCKED);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		// 构造返回体
		DataBufferFactory bufferFactory = response.bufferFactory();
		// 设置冷却时间
		redisTemplate.expire(key, gatewayIpLimitTtl, TimeUnit.SECONDS);
		log.info("ip limit : ip={}, uri={}", ip, uri);
		return bufferFactory.wrap(objectMapper.writeValueAsBytes(RestResult.limit()));
	}

	@Override
	public int getOrder() {
		return -1000;
	}

}
package cn.sticki.gateway.handler;

import cn.sticki.common.result.RestResult;
import cn.sticki.gateway.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 *
 * @author 阿杆
 */
@Slf4j
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

	@Resource
	private VisitService visitService;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();

		// 由于抛出异常后不会再经过 IpLimitFilter，也不会保存访问记录，故手动在此进行调用
		boolean isLimit = visitService.checkLimit(exchange);
		if (isLimit) {
			return visitService.buildMonoWrap(response, RestResult.limit());
		}

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		// HTTP状态码异常
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			HttpStatus httpStatus = ((ResponseStatusException) ex).getStatus();
			response.setStatusCode(httpStatus);
			return visitService.buildMonoWrap(response, RestResult.fail(httpStatus.getReasonPhrase()));
		}

		log.warn("Warn Gateway : {}: {}, {}", ex.getClass(), ex.getMessage(), exchange.getRequest().getPath());
		return visitService.buildMonoWrap(response, RestResult.notFound());
	}

}

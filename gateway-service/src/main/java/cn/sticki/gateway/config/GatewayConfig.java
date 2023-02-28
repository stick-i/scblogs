package cn.sticki.gateway.config;

import cn.sticki.gateway.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 *
 * @author 阿杆
 */
@Configuration(proxyBeanMethods = false)
public class GatewayConfig {

	@Bean
	public GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}

}

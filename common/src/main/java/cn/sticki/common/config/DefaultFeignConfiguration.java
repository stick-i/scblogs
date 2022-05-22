package cn.sticki.common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {

	@Bean
	public Logger.Level logLevel() {
		return Logger.Level.BASIC;
	}

}

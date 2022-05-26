package cn.sticki.common.tool.feignconfig;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignAutoConfiguration {

	@Bean
	public Logger.Level logLevel() {
		return Logger.Level.BASIC;
	}

}

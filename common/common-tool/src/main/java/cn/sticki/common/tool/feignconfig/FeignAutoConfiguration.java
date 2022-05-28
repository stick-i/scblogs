package cn.sticki.common.tool.feignconfig;

import feign.Logger.Level;
import org.springframework.context.annotation.Bean;

public class FeignAutoConfiguration {

	@Bean
	public Level logLevel() {
		return Level.BASIC;
	}

}

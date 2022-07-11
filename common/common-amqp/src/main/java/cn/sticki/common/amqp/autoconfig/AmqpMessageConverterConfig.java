package cn.sticki.common.amqp.autoconfig;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/25 18:01
 */
@Configuration
public class AmqpMessageConverterConfig {

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}

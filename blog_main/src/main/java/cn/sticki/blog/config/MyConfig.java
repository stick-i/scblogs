package cn.sticki.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;

@Data
@ConfigurationProperties(prefix = "my-config")
@Component
// 开启对当前bean的属性注入校验
@Validated
public class MyConfig {

	@Max(value = 8888, message = "最大值不超过8888")
	private long timeOut;

	private String server;

}


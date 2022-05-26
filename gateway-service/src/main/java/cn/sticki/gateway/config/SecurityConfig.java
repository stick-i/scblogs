package cn.sticki.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

	public static String identity;

	public void setIdentity(String identity) {
		SecurityConfig.identity = identity;
	}

}

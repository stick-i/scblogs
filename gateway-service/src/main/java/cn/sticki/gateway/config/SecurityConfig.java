package cn.sticki.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 阿杆
 */
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

	/**
	 * 验证身份后用户id存放的 header 字段
	 */
	public static String identityHeader;

	/**
	 * 需要认证的接口
	 * 直接从网关过滤
	 */
	public static String mustAuthUrl;

	public void setIdentityHeader(String identityHeader) {
		SecurityConfig.identityHeader = identityHeader;
	}

	public void setMustAuthUrl(String mustAuthUrl) {
		SecurityConfig.mustAuthUrl = mustAuthUrl;
	}

}

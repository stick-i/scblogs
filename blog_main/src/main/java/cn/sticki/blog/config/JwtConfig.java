package cn.sticki.blog.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {

	/**
	 * 密钥KEY
	 */
	public static String key;

	/**
	 * 发放者
	 */
	public static String issuer;

	/**
	 * 过期时间
	 */
	public static Integer expiration;

	/**
	 * 不需要认证的接口
	 */
	public static String antMatchers;

	/**
	 * http头token名
	 */
	public static String headerName;

	public void setKey(String secret) {
		JwtConfig.key = secret;
	}

	public void setIssuer(String tokenHeader) {
		JwtConfig.issuer = tokenHeader;
	}

	public void setExpiration(Integer expiration) {
		JwtConfig.expiration = expiration;
	}

	public void setAntMatchers(String antMatchers) {
		JwtConfig.antMatchers = antMatchers;
	}

	public void setHeaderName(String headerName) {
		JwtConfig.headerName = headerName;
	}

}

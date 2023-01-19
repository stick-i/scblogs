package cn.sticki.gateway.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ip限制器配置类
 *
 * @author 阿杆
 * @version 1.0
 * @date 2023/1/18 11:29
 */
@Configuration
@ConfigurationProperties(prefix = "gateway.ip-limit")
@Setter
public class IpLimitConfig {

	/**
	 * Redis key前缀
	 */
	public static String key = "gateway:ipLimit:";

	/**
	 * 在规定的{@link #time}内最多访问多少次
	 */
	public static int count = 30;

	/**
	 * 设置访问的单次记录时间，单位秒
	 */
	public static long time = 5;

	/**
	 * 访问超出限制后禁止访问多久，单位秒
	 */
	public static long ttl = 10;

	public void setKey(String key) {
		IpLimitConfig.key = key;
	}

	public void setCount(int count) {
		IpLimitConfig.count = count;
	}

	public void setTime(long time) {
		IpLimitConfig.time = time;
	}

	public void setTtl(long ttl) {
		IpLimitConfig.ttl = ttl;
	}

}

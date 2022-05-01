package cn.sticki.blog.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DruidConfig {

	/**
	 * 禁用Ping Method，参考链接：https://zhuanlan.zhihu.com/p/368683245
	 */
	@PostConstruct
	public void setProperties() {
		System.setProperty("druid.mysql.usePingMethod", "false");
	}

}

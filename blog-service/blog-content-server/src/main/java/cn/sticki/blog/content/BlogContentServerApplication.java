package cn.sticki.blog.content;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.web.adviceconfig.EnableExceptionDefaultAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/7 22:32
 */
@EnableRedisSerialize
@EnableExceptionDefaultAdvice
@EnableFeignClients(basePackages = "cn.sticki.user.client")
@SpringBootApplication
public class BlogContentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogContentServerApplication.class, args);
	}

}

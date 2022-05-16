package cn.sticki.blog_register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer // Eureka 自动转配开关
@SpringBootApplication
public class BlogRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogRegisterApplication.class, args);
	}

}

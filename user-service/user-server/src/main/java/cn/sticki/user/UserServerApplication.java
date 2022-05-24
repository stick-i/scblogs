package cn.sticki.user;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步控制
@EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
@EnableFeignClients(basePackages = {"cn.sticki.resource", "cn.sticki.message"}) // 开启feign
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

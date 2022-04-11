package cn.sticki.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableCaching  // 开启springboot集成的缓存功能
// @EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
// @EnableMethodCache(basePackages = "cn.sticki.blog")
public class BlogMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogMainApplication.class, args);
	}

}

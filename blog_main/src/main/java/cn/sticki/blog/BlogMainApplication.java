package cn.sticki.blog;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableCaching  // 开启springboot集成的缓存功能
// @EnableMethodCache(basePackages = "cn.sticki.blog")
@EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
public class BlogMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogMainApplication.class, args);
	}

}

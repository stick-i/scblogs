package cn.sticki.blog;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.adviceconfig.EnableExceptionDefaultAdvice;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 阿杆
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@SpringBootApplication
@EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.user.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页
@EnableExceptionDefaultAdvice // 开启web默认异常捕获
@EnableRedisSerialize
public class BlogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogServerApplication.class, args);
	}

}

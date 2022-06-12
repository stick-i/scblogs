package cn.sticki.user;

import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.adviceconfig.EnableExceptionDefaultAdvice;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 阿杆
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@SpringBootApplication
@EnableAsync // 开启异步控制
@EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.message.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页助手
@EnableExceptionDefaultAdvice // 默认异常处理
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

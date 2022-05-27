package cn.sticki.user;

import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步控制
@EnableCreateCacheAnnotation  // jetcache启用缓存的主开关
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.message.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页助手
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

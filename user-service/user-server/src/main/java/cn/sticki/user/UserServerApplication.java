package cn.sticki.user;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.advice.EnableDefaultExceptionAdvice;
import cn.sticki.common.web.advice.EnableDefaultResponseAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 阿杆
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@SpringBootApplication
@EnableAsync // 开启异步控制
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.blog.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页助手
@EnableRedisSerialize // 开启RedisTemplate序列化配置
@EnableScheduling // 开启定时任务
@EnableDefaultExceptionAdvice // 注入默认异常处理器
@EnableDefaultResponseAdvice // 注入默认包装器
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

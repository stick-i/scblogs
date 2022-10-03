package cn.sticki.user;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.advice.EnableDefaultExceptionAdvice;
import cn.sticki.common.web.advice.EnableDefaultResponseAdvice;
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
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.message.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页助手
@EnableDefaultExceptionAdvice // 默认异常处理
@EnableDefaultResponseAdvice // 开启默认的响应体包装处理器
@EnableRedisSerialize // 开启RedisTemplate序列化配置
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

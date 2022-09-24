package cn.sticki.user;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.adviceconfig.EnableDefaultExceptionAdvice;
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
@EnableRedisSerialize // 开启RedisTemplate序列化配置
public class UserServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServerApplication.class, args);
	}

}

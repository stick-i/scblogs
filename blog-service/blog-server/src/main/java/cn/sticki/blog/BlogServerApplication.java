package cn.sticki.blog;

import cn.sticki.common.amqp.autoconfig.EnableAmqpMessageConverterConfig;
import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.advice.EnableDefaultExceptionAdvice;
import cn.sticki.common.web.advice.EnableDefaultResponseAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 阿杆
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.sticki.resource.client", "cn.sticki.user.client"}) // 开启feign
@EnableMybatisPlusIPage // 开启mybatis分页
@EnableRedisSerialize
@EnableAmqpMessageConverterConfig
@EnableDefaultExceptionAdvice
@EnableDefaultResponseAdvice
public class BlogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogServerApplication.class, args);
	}

}

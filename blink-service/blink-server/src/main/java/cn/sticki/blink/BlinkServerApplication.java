package cn.sticki.blink;

import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import cn.sticki.common.web.advice.EnableDefaultExceptionAdvice;
import cn.sticki.common.web.advice.EnableDefaultResponseAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 阿杆
 */
@SpringBootApplication
@EnableMybatisPlusIPage
@EnableDefaultExceptionAdvice
@EnableDefaultResponseAdvice
@EnableFeignClients(basePackages = {"cn.sticki.user.client"})
public class BlinkServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkServerApplication.class, args);
	}

}

package cn.sticki.comment;

import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 阿杆
 */
@SpringBootApplication
@EnableMybatisPlusIPage
@EnableFeignClients(basePackages = {"cn.sticki.blog.client", "cn.sticki.user.client"})
public class CommentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentServerApplication.class, args);
	}

}

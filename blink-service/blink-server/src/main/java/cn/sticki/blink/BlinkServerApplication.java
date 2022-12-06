package cn.sticki.blink;

import cn.sticki.common.tool.mybatisconfig.EnableMybatisPlusIPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 阿杆
 */
@SpringBootApplication
@EnableMybatisPlusIPage
public class BlinkServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkServerApplication.class, args);
	}

}

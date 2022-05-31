package cn.sticki.resource;

import cn.sticki.common.web.adviceconfig.EnableExceptionDefaultAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableExceptionDefaultAdvice
public class ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

}

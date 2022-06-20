package cn.sticki.gateway;

import cn.sticki.common.redis.autoconfig.EnableRedisSerialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 阿杆
 */
@EnableRedisSerialize
@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}

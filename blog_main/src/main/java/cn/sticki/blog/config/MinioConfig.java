package cn.sticki.blog.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

	private String url;

	private String username;

	private String password;
	
	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder().endpoint(url).credentials(username, password).build();
	}

}

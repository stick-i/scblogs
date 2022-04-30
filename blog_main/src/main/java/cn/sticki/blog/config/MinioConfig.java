package cn.sticki.blog.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

	private String url;

	private String username;

	private String password;

	public static String bucketName;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder().endpoint(url).credentials(username, password).build();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBucketName(String bucketName) {
		MinioConfig.bucketName = bucketName;
	}

}

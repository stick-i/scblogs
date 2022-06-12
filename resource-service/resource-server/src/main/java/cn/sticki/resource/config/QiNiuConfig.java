package cn.sticki.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 阿杆
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfig {

	String accessKey;

	String secretKey;

	String prefixUrl;

}

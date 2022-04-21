package cn.sticki.blog.config;

import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 解决session被浏览器拦截的问题
 */
@Configuration
@EnableSpringHttpSession
public class CorsConfig {

	@Bean
	public SessionRepository sessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}

	@Bean
	DefaultCookieSerializerCustomizer cookieSerializerCustomizer() {
		return cookieSerializer -> {
			cookieSerializer.setSameSite("None");
			cookieSerializer.setUseSecureCookie(true); // 此项必须，否则set-cookie会被chrome浏览器阻拦
		};
	}

}

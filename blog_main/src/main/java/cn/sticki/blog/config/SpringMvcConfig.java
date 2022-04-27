package cn.sticki.blog.config;

import cn.sticki.blog.controller.interceptor.UserLoginInterceptor;
import cn.sticki.blog.pojo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	/**
	 * 解决跨域问题
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS").allowCredentials(true).maxAge(3600).allowedHeaders("*");
	}

	/**
	 * 拦截器
	 */
	@Override
	public void addInterceptors(@NotNull InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Bean
	public UserLoginInterceptor userLoginInterceptor() {
		return new UserLoginInterceptor();
	}

	/**
	 * 用户bean
	 */
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User getUser() {
		// 当user为null时，无法正常注入到需要被注入的位置
		log.debug("create bean User");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			log.debug("Get UserBean Success!");
			return (User) principal;
		}
		return new User();
	}

}

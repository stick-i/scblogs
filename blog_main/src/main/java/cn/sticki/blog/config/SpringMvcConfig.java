package cn.sticki.blog.config;

import cn.sticki.blog.controller.interceptor.UserLoginInterceptor;
import cn.sticki.blog.pojo.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
		// 未登录用户拦截
		registry.addInterceptor(userLoginInterceptor())
				.addPathPatterns("/user/**")
				.excludePathPatterns("/user")
				.addPathPatterns("/blog-console/**");
	}

	@Bean
	public UserLoginInterceptor userLoginInterceptor() {
		return new UserLoginInterceptor();
	}

}

@Configuration
class UserBean {

	@Resource
	private HttpSession session;

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User getUser() {
		// 当user为null时，无法正常注入到需要被注入的位置
		return (User) session.getAttribute("user");
	}

}

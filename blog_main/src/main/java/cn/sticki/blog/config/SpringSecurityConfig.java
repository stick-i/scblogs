package cn.sticki.blog.config;

import cn.sticki.blog.controller.interceptor.AuthenticationTokenFilter;
import cn.sticki.blog.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置类
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private ResponseUtils responseUtils;

	// @Resource
	// private AuthenticationFailureHandlerAdvice authenticationFailureHandlerAdvice;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//关闭csrf
				.csrf().disable()
				//不通过Session获取SecurityContext
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// .formLogin().loginProcessingUrl("/login/login").failureHandler((request, response, exception) -> {
				// 	log.debug("failureHandler,request->{}",request.getRequestURL());
				// 	responseUtils.objectToJson(response, new RestTemplate(false, "用户名或密码错误"));
				// }).and()
				// 添加授权请求
				.authorizeRequests()
				// 登录接口 允许匿名访问(只能未登录访问，登录不可访问)
				.antMatchers("/login/login").anonymous()
				// 博客列表接口 允许全部访问
				.antMatchers("/blog/**", "/resource/**", "/user").permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated();
		// 添加过滤器，添加到某个过滤器之前，这里是添加到 UsernamePasswordAuthenticationFilter 之前
		http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		// http.addFilterAt(authenticationFailureHandlerAdvice, UsernamePasswordAuthenticationFilter.class);

		// 异常处理器
		http.exceptionHandling()
				// 配置认证失败处理器
				.authenticationEntryPoint(authenticationEntryPoint)
				// 配置权限不足处理器
				.accessDeniedHandler(accessDeniedHandler);
		// 允许跨域
		http.cors();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

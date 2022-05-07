package cn.sticki.blog.config;

import cn.sticki.blog.security.filter.AuthenticationTokenFilter;
import cn.sticki.blog.security.filter.IPLimitFilter;
import cn.sticki.blog.security.handler.AuthAccessDeniedHandler;
import cn.sticki.blog.security.handler.AuthenticationEntryPointHandler;
import cn.sticki.blog.security.handler.LoginFailureHandler;
import cn.sticki.blog.security.handler.LoginSuccessHandler;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 登录成功处理器
	 */
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	/**
	 * 登录失败处理器
	 */
	@Autowired
	private LoginFailureHandler loginFailureHandler;

	// /**
	//  * 注销成功处理器
	//  */
	// @Autowired
	// private UserLogoutSuccessHandler userLogoutSuccessHandler;

	/**
	 * 暂无权限处理器
	 */
	@Autowired
	private AuthAccessDeniedHandler authAccessDeniedHandler;

	/**
	 * 未登录的处理器
	 */
	@Autowired
	private AuthenticationEntryPointHandler authenticationEntryPointHandler;

	// /**
	//  * 自定义登录逻辑验证器
	//  */
	// @Autowired
	// private UserAuthenticationProvider userAuthenticationProvider;

	/**
	 * 用户认证过滤器
	 */
	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;
	//
	// @Autowired
	// private AuthenticationEntryPoint authenticationEntryPoint;
	//
	// @Autowired
	// private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private IPLimitFilter ipLimitFilter;

	// @Resource
	// private AuthenticationFailureHandlerAdvice authenticationFailureHandlerAdvice;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 不进行权限验证的请求或资源(从配置文件中读取)
				.antMatchers(JwtConfig.antMatchers.split(",")).permitAll()
				// 其他的需要登陆后才能访问
				.anyRequest().authenticated().and()
				// 配置httpBasic未登录自定义处理类
				.httpBasic().authenticationEntryPoint(authenticationEntryPointHandler).and().exceptionHandling().and()
				// 配置登录地址
				.formLogin().loginProcessingUrl("/login/login")
				// 配置登录成功自定义处理类
				.successHandler(loginSuccessHandler)
				// 配置登录失败自定义处理类
				.failureHandler(loginFailureHandler).and()
				// 配置登出地址
				// .logout()
				// .logoutUrl("/login/userLogout")
				// 配置用户登出自定义处理类
				// .logoutSuccessHandler(userLogoutSuccessHandler)
				// .and()
				// 配置没有权限自定义处理类
				.exceptionHandling().accessDeniedHandler(authAccessDeniedHandler)
				// 配置未登录自定义处理类
				.authenticationEntryPoint(authenticationEntryPointHandler).and()
				// 开启跨域
				.cors().and()
				// 取消跨站请求伪造防护
				.csrf().disable();

		// 基于Token不需要session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// 禁用缓存
		http.headers().cacheControl();
		// 添加JWT过滤器
		http.addFilterAt(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 添加ip限制过滤器
		http.addFilterBefore(ipLimitFilter, UsernamePasswordAuthenticationFilter.class);
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

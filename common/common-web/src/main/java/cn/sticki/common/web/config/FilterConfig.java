package cn.sticki.common.web.config;

import cn.sticki.common.web.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date 2022/10/26
 *
 * @author likangli
 * description
 */
@Configuration
public class FilterConfig {

	// @Bean
	// public FilterRegistrationBean<XssFilter> xssFilter() {
	//
	// 	FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
	// 	// 配置不走xss过滤器的路径，GET\DELETE请求默认不走
	// 	List<String> excludes = new ArrayList<>();
	// 	XssFilter myFilter = new XssFilter(excludes);
	//
	// 	registrationBean.setFilter(myFilter);
	// 	registrationBean.setUrlPatterns(Collections.singletonList("/*"));
	// 	registrationBean.setOrder(1);
	// 	return registrationBean;
	// }

}

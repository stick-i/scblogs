package cn.sticki.common.web.filter;

import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Date 2022/10/26
 *
 * @author likangli
 * description xss攻击过滤器
 */

public class XssFilter implements Filter {

	/**
	 * 排除链接
	 */
	public List<String> excludes;

	public XssFilter(List<String> excludes) {
		this.excludes = excludes;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		/*
		 * 判断是否有xss，没有直接放行；有的话处理一下。
		 */
		if (handleExcludeUrl(req)) {
			chain.doFilter(request, response);
			return;
		}
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	/**
	 * 是否包含排除链接
	 */
	private boolean handleExcludeUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String method = request.getMethod();

		if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
			return true;
		}
		if (CollectionUtils.isEmpty(excludes)) {
			return false;
		}
		return excludes.contains(uri);
	}

}


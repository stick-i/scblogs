package cn.sticki.common.web.filter;

import cn.hutool.http.HTMLFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Date 2022/10/26
 *
 * @author likangli
 * description 处理xss的request包装类
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] escapeValues = new String[length];
			for (int i = 0; i < length; i++) {
				//防止xss攻击和过滤前后空格
				escapeValues[i] = clean((values[i].trim()));
			}
			return escapeValues;
		}
		return super.getParameterValues(name);
	}

	private String clean(String content) {
		return new HTMLFilter().filter(content);
	}

}


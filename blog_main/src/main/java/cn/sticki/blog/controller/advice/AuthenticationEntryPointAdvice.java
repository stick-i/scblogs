package cn.sticki.blog.controller.advice;

import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointAdvice implements AuthenticationEntryPoint {

	@Autowired
	private ResponseUtils responseUtils;

	/**
	 * 处理身份验证异常
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		responseUtils.objectToJson(response, new RestTemplate(401, "未登录"));
	}

}

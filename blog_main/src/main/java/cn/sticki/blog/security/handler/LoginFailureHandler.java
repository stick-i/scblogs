package cn.sticki.blog.security.handler;

import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private ResponseUtils responseUtils;

	/**
	 * 登录失败返回结果
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		responseUtils.objectToJson(response, new RestTemplate(false, "用户名或密码错误"));
	}

}

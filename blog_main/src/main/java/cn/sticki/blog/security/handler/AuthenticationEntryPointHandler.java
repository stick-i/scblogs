package cn.sticki.blog.security.handler;

import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	@Autowired
	private ResponseUtils responseUtils;

	/**
	 * 处理身份验证异常
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.debug("用户未登录");
		responseUtils.objectToJson(response, new RestTemplate(401, "用户未登录"));
	}

}

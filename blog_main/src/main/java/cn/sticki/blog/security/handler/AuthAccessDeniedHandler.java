package cn.sticki.blog.security.handler;

import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
	private ResponseUtils responseUtils;

	/**
	 * 处理请求权限异常
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		responseUtils.objectToJson(response, new RestTemplate(402, "拒绝访问"));
	}

}

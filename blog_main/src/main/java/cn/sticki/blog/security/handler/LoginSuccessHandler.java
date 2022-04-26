package cn.sticki.blog.security.handler;

import cn.sticki.blog.config.JwtConfig;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.UserDetailsSecurity;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.util.JwtUtils;
import cn.sticki.blog.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 */
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Resource
	private UserMapper userMapper;

	@Resource
	private JwtUtils jwtUtils;

	@Resource
	private ResponseUtils responseUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		UserSafety userSafety = ((UserDetailsSecurity) authentication.getPrincipal()).getUserSafety();
		User user = userMapper.selectById(userSafety.getUserId());
		log.debug("/login/login,username->{}", user.getUsername());
		UserVO userVO = UserVO.userToVO(user);
		response.setHeader("token", jwtUtils.createToken(userVO));
		response.setHeader("Access-Control-Expose-Headers", JwtConfig.headerName);
		responseUtils.objectToJson(response, new RestTemplate(userVO));
	}

}
package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.config.JwtConfig;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.LoginService;
import cn.sticki.user.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录相关接口
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Resource
	private LoginService loginService;

	@Resource
	private HttpServletResponse response;

	/**
	 * 用户登录
	 *
	 * @param username 账号
	 * @param password 密码
	 */
	@PostMapping("/login")
	public RestResult<Object> loginHandle(@NotNull String username, @NotNull String password) {
		UserView user = loginService.login(username, password);
		if (user == null)
			return new RestResult<>(false, "用户名或密码错误");
		response.setHeader(JwtConfig.headerName, JwtUtils.createToken("id", user.getId()));
		response.setHeader("Access-Control-Expose-Headers", JwtConfig.headerName);
		return new RestResult<>(user);
	}

}

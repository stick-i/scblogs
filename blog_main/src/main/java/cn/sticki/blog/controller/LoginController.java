package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.service.LoginService;
import cn.sticki.blog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Resource
	private LoginService loginService;

	@Resource
	private JwtUtils jwtUtils;

	/**
	 * 登录
	 */
	// @PostMapping("/login")
	public RestTemplate login(String username, String password, HttpServletResponse response) {
		log.debug("/login/login,username->{}", username);
		User user = loginService.login(username, password);
		if (user == null) {
			return new RestTemplate(false, "用户名或密码错误!");
		}
		UserVO userVO = UserVO.userToVO(user);
		response.setHeader("token", jwtUtils.createToken(userVO));
		response.setHeader("Access-Control-Expose-Headers", "token");
		return new RestTemplate(userVO);
	}

	@GetMapping("/logout")
	public RestTemplate logout() {
		// session.removeAttribute("user");
		return new RestTemplate(400, "此接口暂时停用，请前端手动删除localSession中的token");
	}

}

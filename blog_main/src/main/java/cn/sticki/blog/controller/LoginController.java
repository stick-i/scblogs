package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Resource
	private UserService userService;

	@Resource
	private HttpSession session;

	/**
	 * 登录
	 */
	@PostMapping("/login")
	public RestTemplate login(String username, String password) {
		User user = userService.login(username, password);
		if (user != null) {
			session.setAttribute("user", user);
			return new RestTemplate(UserVO.userToVO(user));
		}
		return new RestTemplate(false, "用户名或密码错误");
	}

	@GetMapping("/logout")
	public RestTemplate logout() {
		session.removeAttribute("user");
		return new RestTemplate(true);
	}

}

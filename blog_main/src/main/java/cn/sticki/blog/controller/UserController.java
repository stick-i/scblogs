package cn.sticki.blog.controller;

import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private HttpSession session;

	@Resource
	private UserService userService;

	@Autowired
	private User user;

	/**
	 * 获取公开信息
	 *
	 * @param username 用户名
	 */
	@GetMapping
	public RestTemplate getByUsername(String username) {
		// todo 查不到的人需要做处理
		RestTemplate template = new RestTemplate();
		if (username == null) {
			if (user != null) template.setData(new UserVO(user));
		} else {
			template.setData(new UserVO(userService.getByUsername(username)));
		}
		return template;
	}

	/**
	 * 修改昵称
	 *
	 * @param nickname 昵称
	 */
	@PutMapping("/nickname")
	public RestTemplate updateNickname(@NotNull String nickname) {
		user.setNickname(nickname); // 由于user是从session中注入进来的，所以更新user的时候，会自动更新session，无需将更新后的user重新放到Session中
		if (userService.updateNickname(user.getId(), user.getNickname())) {
//			session.setAttribute("user", user);
			return new RestTemplate(true);
		}
		return new RestTemplate(false);
	}

	/**
	 * 修改头像
	 *
	 * @param multipartFile 文件流
	 */
	@PutMapping("/avatar")
	public RestTemplate updateAvatar(@NotNull MultipartFile multipartFile) throws MinioException, IOException {
		// todo 此处添加文件校验
		userService.updateAvatar(user, multipartFile);
		return new RestTemplate();
	}

	/**
	 * 修改密码
	 *
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 */
	@PutMapping("/password")
	public RestTemplate updatePassword(@NotNull String oldPassword, @NotNull String newPassword) {
//		userService.checkPassword()
		return new RestTemplate();
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping("/user")
	public RestTemplate delete(@NotNull String password) {
		if (!userService.checkPassword(user.getId(), password)) {
			return new RestTemplate(false, "密码错误");
		} else return new RestTemplate(userService.removeById(user.getId()));
	}

}


@Configuration
class UserBean {

	@Resource
	private HttpSession session;

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User getUser() {
		return (User) session.getAttribute("user");
	}
}

package cn.sticki.blog.controller;

import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * 获取用户信息
	 *
	 * @param username 用户名
	 */
	@GetMapping("/user")
	public RestTemplate getByUsername(String username) {
		RestTemplate template = new RestTemplate();
		if (username == null) {
			User user = (User) session.getAttribute("user");
			if (user != null) template.setData(new UserVO(user));
		} else {
			template.setData(new UserVO(userService.getByUsername(username)));
		}
		return template;
	}

	/**
	 * 更新用户昵称
	 *
	 * @param nickname 昵称
	 */
	@PutMapping("/nickname")
	public RestTemplate updateNickname(@NotNull String nickname) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new RestTemplate(400, "请先登录");
		user.setNickname(nickname);
		if (userService.updateNickname(user.getId(), user.getNickname())) {
			session.setAttribute("user", user);
			return new RestTemplate(true);
		}
		return new RestTemplate(false);
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping
	public RestTemplate delete() {
		User user = (User) session.getAttribute("user");
		return new RestTemplate(user != null && userService.removeByUsername(user.getUsername()));
	}

	/**
	 * 更新头像
	 *
	 * @param multipartFile 文件流
	 */
	@PutMapping("/avatar")
	public RestTemplate updateAvatar(@NotNull MultipartFile multipartFile) throws MinioException, IOException {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			// todo 此处添加文件校验
			userService.updateAvatar(user, multipartFile);
			return new RestTemplate();
		}
		return new RestTemplate(false, "未登录");
	}

}

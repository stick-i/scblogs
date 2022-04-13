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
	 * 获取当前用户信息
	 */
	@GetMapping
	public RestTemplate get() {
		return new RestTemplate(new UserVO((User) session.getAttribute("user")));
	}

	/**
	 * 获取用户信息
	 *
	 * @param username 用户名
	 */
	@GetMapping({"/{username}"})
	public RestTemplate getByUsername(@PathVariable String username) {
		return new RestTemplate(new UserVO(userService.getByUsername(username)));
	}

	/**
	 * 更新用户信息
	 */
	@PutMapping
	public RestTemplate update(User user) {
		// userService.update();
		return new RestTemplate();
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
			userService.updateAvatar(user, multipartFile);
			return new RestTemplate();
		}
		return new RestTemplate(false, "未登录");
	}

}

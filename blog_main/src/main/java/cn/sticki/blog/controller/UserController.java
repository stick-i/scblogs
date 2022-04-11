package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/users")
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
		return new RestTemplate(session.getAttribute("user"));
	}

	/**
	 * 获取用户信息
	 *
	 * @param username 用户名
	 */
	@GetMapping({"/{username}"})
	public RestTemplate getByUsername(@PathVariable String username) {
		return new RestTemplate(userService.getByUsername(username));
	}

	/**
	 * 更新用户信息
	 */
	@PutMapping
	public RestTemplate update(@RequestBody User user) {
		// userService.update(user);
		return new RestTemplate();
	}

	/**
	 * 新增用户
	 */
	@PostMapping
	public RestTemplate create(@RequestBody User user) {
		return new RestTemplate();
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping
	public RestTemplate delete(@RequestBody User user) {
		return new RestTemplate();
	}

}

package cn.sticki.blog.controller;

import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.pojo.vo.UserVO;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.FileType;
import cn.sticki.blog.util.FileUtils;
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
	private User user;  // 当user为null时无法注入，此处将会抛出异常

	@Resource
	private FileUtils fileUtils;

	/**
	 * 获取公开信息
	 *
	 * @param username 用户名
	 */
	@GetMapping
	public RestTemplate getByUsername(String username) {
		user = (User) session.getAttribute("user");
		User getUser = null;
		if (username == null && user != null) {
			getUser = user;
			log.debug("getByUsername, sessionUser ,user->{}", getUser);
		} else if (username != null) {
			getUser = userService.getByUsername(username);
			log.debug("getByUsername, userService.getByUsername ,user->{}", getUser);
		}
		return new RestTemplate(UserVO.userToVO(getUser));
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
			// session.setAttribute("user", user);
			return new RestTemplate(true);
		}
		return new RestTemplate(false);
	}

	/**
	 * 修改头像
	 *
	 * @param avatarFile 文件流
	 */
	@PutMapping("/avatar")
	public RestTemplate updateAvatar(@NotNull MultipartFile avatarFile) throws MinioException, IOException {
		log.debug("updateAvatar,fileSize->{}", avatarFile.getSize());
		// 小于1Mib
		if (avatarFile.getSize() > 1024 * 1024) {
			return new RestTemplate(false, "文件过大");
		}
		FileType fileType = fileUtils.getType(avatarFile);
		// 仅支持JPEG和PNG
		if (fileType != FileType.JPEG && fileType != FileType.PNG) {
			return new RestTemplate(false, "不支持的文件类型");
		}
		userService.updateAvatar(user, avatarFile);
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
		if (userService.checkPassword(user.getId(), oldPassword)) {
			userService.updatePasswordById(user.getId(), newPassword);
			return new RestTemplate(true, "修改成功");
		}
		return new RestTemplate(false, "修改失败");
	}

	/**
	 * 修改邮箱
	 *
	 * @param mail       新邮箱
	 * @param mailVerify 邮箱验证码
	 */
	@PutMapping("/mail")
	public RestTemplate updateMail(@NotNull String mail, @NotNull String mailVerify) {
		if (userService.checkMailVerify(user.getId(), mailVerify, "updateMail")) {
			userService.updateMail(user.getId(), mail);
			return new RestTemplate(true, "修改成功");
		}
		return new RestTemplate(false, "修改失败");
	}

	/**
	 * 发送邮箱验证码
	 */
	@PostMapping("/mail/send-mail-verify")
	public RestTemplate sendMailVerifyForUpdateMail() throws MailSendException {
		Long sendTime = (Long) session.getAttribute("mail-send-mail-verify-time");
		Long nowTime = System.currentTimeMillis() / 1000;
		// 判断是否发送过邮件，若上一次发送邮件的时间超过60s则允许发送
		if (sendTime == null || nowTime - sendTime > 60) {
			userService.sendMailVerify(user.getId(), "updateMail");
			session.setAttribute("mail-send-mail-verify-time", nowTime); // 将发送邮件的时间存到session
			return new RestTemplate(true, "发送成功");
		}
		return new RestTemplate(false, "拒绝发送");
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping("/user")
	public RestTemplate delete(@NotNull String password) {
		if (!userService.checkPassword(user.getId(), password))
			return new RestTemplate(false, "密码错误");
		else
			return new RestTemplate(userService.removeById(user.getId()));
	}

}

@Configuration
class UserBean {

	@Resource
	private HttpSession session;

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User getUser() {
		// 当user为null时，无法正常注入到需要被注入的位置
		return (User) session.getAttribute("user");
	}
}

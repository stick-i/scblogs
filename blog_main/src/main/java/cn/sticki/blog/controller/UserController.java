package cn.sticki.blog.controller;

import cn.sticki.blog.enumeration.CacheSpace;
import cn.sticki.blog.enumeration.type.FileType;
import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.security.AuthenticationFacade;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.FileUtils;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private AuthenticationFacade authenticationFacade;

	@Resource
	private FileUtils fileUtils;

	@CreateCache(name = CacheSpace.Login_UserID)
	Cache<Integer, User> cache;

	/**
	 * 获取公开信息
	 *
	 * @param username 用户名
	 */
	@GetMapping
	public RestTemplate getByUsername(String username) {
		User user = authenticationFacade.getUser();
		User getUser = null;
		if (username == null && user != null) {
			getUser = user;
			log.debug("getByUsername, sessionUser ,user->{}", getUser.getClass());
		} else if (username != null) {
			getUser = userService.getByUsername(username);
			log.debug("getByUsername, userService.getByUsername ,user->{}", getUser);
		}
		return new RestTemplate(getUser);
	}

	/**
	 * 修改昵称
	 *
	 * @param nickname 昵称
	 */
	@PutMapping("/nickname")
	public RestTemplate updateNickname(@NotNull String nickname) {
		User user = authenticationFacade.getUser();
		user.setNickname(nickname);
		if (userService.updateNickname(user.getId(), user.getNickname())) {
			cache.put(user.getId(), user);
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
		User user = authenticationFacade.getUser();
		log.debug("updateAvatar,fileSize->{}", avatarFile.getSize());
		// 检查文件，小于1Mib ,仅支持JPEG和PNG
		fileUtils.checkFile(avatarFile, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		userService.updateAvatar(user, avatarFile);
		cache.put(user.getId(), user);
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
		User user = authenticationFacade.getUser();
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
		User user = authenticationFacade.getUser();
		if (userService.checkMailVerify(user.getId(), mailVerify)) {
			userService.updateMail(user.getId(), mail);
			return new RestTemplate(true, "修改成功");
		}
		return new RestTemplate(false, "修改失败");
	}

	@CreateCache(name = CacheSpace.UserService_SendMailTime, expire = 300)
	private Cache<Integer, Long> sendMailTimeCache;

	/**
	 * 发送邮箱验证码
	 */
	@PostMapping("/mail/send-mail-verify")
	public RestTemplate sendMailVerifyForUpdateMail() throws MailSendException {
		User user = authenticationFacade.getUser();
		Long sendTime = sendMailTimeCache.get(user.getId());
		Long nowTime = System.currentTimeMillis() / 1000;
		// 判断是否发送过邮件，若上一次发送邮件的时间超过60s则允许发送
		if (sendTime == null || nowTime - sendTime > 60) {
			userService.sendMailVerify(user.getId());
			sendMailTimeCache.put(user.getId(), nowTime);// 将发送邮件的时间存到Cache(Redis)
			return new RestTemplate(true, "发送成功");
		}
		return new RestTemplate(false, "拒绝发送");
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping("/user")
	public RestTemplate delete(@NotNull String password) {
		User user = authenticationFacade.getUser();
		if (!userService.checkPassword(user.getId(), password)) return new RestTemplate(false, "密码错误");
		else return new RestTemplate(userService.removeById(user.getId()));
	}

}

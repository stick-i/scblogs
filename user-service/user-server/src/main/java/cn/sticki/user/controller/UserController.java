package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.resource.type.FileType;
import cn.sticki.resource.utils.FileUtils;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.UserService;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 用户信息相关接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	// @CreateCache(name = CacheSpace.Login_UserID)
	// Cache<Integer, User> cache;

	// @CreateCache(name = CacheSpace.UserService_SendMailTime, expire = 300)
	@CreateCache(name = "user:user:sendMailTime", expire = 300)
	private Cache<Integer, Long> sendMailTimeCache;

	/**
	 * 获取公开信息
	 *
	 * @param id 用户名id
	 */
	@GetMapping
	public RestResult<User> getByUserId(Integer id, @RequestHeader(value = "id", required = false) Integer userId) {
		User getUser = null;
		if (id == null && userId != null) {
			getUser = userService.getById(userId);
			log.debug("getByUsername, sessionUser ,user->{}", getUser);
		} else if (id != null) {
			getUser = userService.getById(id);
			log.debug("getByUsername, userService.getByUsername ,user->{}", getUser);
		}
		return new RestResult<>(getUser);
	}

	/**
	 * 批量获取用户信息
	 */
	@PostMapping("/list")
	public RestResult<Map<Integer, UserView>> getUserList(@RequestParam List<Integer> userIdList) {
		return new RestResult<>(userService.getUserListMap(new HashSet<>(userIdList)));
	}

	/**
	 * 修改昵称
	 *
	 * @param nickname 昵称
	 */
	@PutMapping("/nickname")
	public RestResult<Object> updateNickname(@NotNull String nickname, @RequestHeader Integer id) {
		if (userService.updateNickname(id, nickname)) {
			// cache.put(id, user);
			return new RestResult<>(true);
		}
		return new RestResult<>(false);
	}

	/**
	 * 修改头像
	 *
	 * @param avatarFile 文件流
	 */
	@PutMapping("/avatar")
	public RestResult<String> updateAvatar(@NotNull MultipartFile avatarFile, @RequestHeader Integer id) {
		log.debug("updateAvatar,fileSize->{}", avatarFile.getSize());
		// 检查文件，小于1Mib ,仅支持JPEG和PNG
		FileUtils.checkFile(avatarFile, 1024 * 1024L, FileType.JPEG, FileType.PNG);
		// cache.put(id, user);
		String avatar = userService.updateAvatar(id, avatarFile);
		if (avatar != null)
			return new RestResult<>(avatar);
		else
			return new RestResult<>(false, "上传失败");
	}

	/**
	 * 修改密码
	 *
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 */
	@PutMapping("/password")
	public RestResult<Object> updatePassword(@NotNull String oldPassword, @NotNull String newPassword, @RequestHeader Integer id) {
		if (userService.checkPassword(id, oldPassword)) {
			boolean result = userService.updatePasswordById(id, newPassword);
			return new RestResult<>(result);
		}
		return new RestResult<>(false, "修改失败");
	}

	/**
	 * 更新用户院校代码
	 *
	 * @param code 院校代码
	 */
	@PutMapping("/school/code")
	public RestResult<Object> updateSchoolCode(@NotNull Integer code, @RequestHeader Integer id) {
		return new RestResult<>(userService.updateSchoolCode(id, code));
	}

	/**
	 * 修改邮箱
	 *
	 * @param mail       新邮箱
	 * @param mailVerify 邮箱验证码
	 */
	@PutMapping("/mail")
	public RestResult<Object> updateMail(@NotNull String mail, @NotNull String mailVerify, @RequestHeader Integer id) {
		if (userService.checkMailVerify(id, mailVerify)) {
			boolean result = userService.updateMail(id, mail);
			return new RestResult<>(result);
		}
		return new RestResult<>(false, "修改失败");
	}

	/**
	 * 发送邮箱验证码
	 */
	@PostMapping("/mail/send-mail-verify")
	public RestResult<Object> sendMailVerifyForUpdateMail(@RequestHeader Integer id) {
		Long sendTime = sendMailTimeCache.get(id);
		Long nowTime = System.currentTimeMillis() / 1000;
		// 判断是否发送过邮件，若上一次发送邮件的时间超过60s则允许发送
		if (sendTime == null || nowTime - sendTime > 60) {
			boolean result = userService.sendMailVerify(id);
			if (result) {
				sendMailTimeCache.put(id, nowTime);// 将发送邮件的时间存到Cache(Redis)
				return new RestResult<>(true, "发送成功");
			}
		}
		return new RestResult<>(false, "发送失败");
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping("/user")
	public RestResult<Object> delete(@NotNull String password, @RequestHeader Integer id) {
		if (!userService.checkPassword(id, password)) return new RestResult<>(false, "密码错误");
		else return new RestResult<>(userService.removeById(id));
	}

}

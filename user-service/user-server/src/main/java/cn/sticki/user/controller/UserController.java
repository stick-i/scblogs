package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.web.anno.RequestLimit;
import cn.sticki.resource.type.FileType;
import cn.sticki.resource.utils.FileUtils;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserGeneral;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.UserService;
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
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 获取公开信息
	 *
	 * @param id 用户名id
	 */
	@GetMapping
	@RequestLimit
	public User getByUserId(Integer id, @RequestHeader(value = "id", required = false) Integer userId) {
		Integer getId = null;
		if (id == null && userId != null) {
			getId = userId;
		} else if (id != null) {
			getId = id;
		}
		return userService.getById(getId);
	}

	/**
	 * 批量获取用户信息
	 */
	@PostMapping("/list")
	public Map<Integer, UserView> getUserList(@RequestParam List<Integer> userIdList) {
		return userService.getUserListMap(new HashSet<>(userIdList));
	}

	/**
	 * 批量查询用户各项数据统计
	 *
	 * @param userIdList 用户id列表
	 * @return 批量用户统计数据
	 */
	@GetMapping("/general")
	public RestResult<Map<Integer, UserGeneral>> getUserGeneralList(@RequestParam List<Integer> userIdList) {
		return new RestResult<>(userService.getUserGeneralListMap(userIdList));
	}

	/**
	 * 修改昵称
	 *
	 * @param nickname 昵称
	 */
	@PutMapping("/nickname")
	public RestResult<Object> updateNickname(@NotNull String nickname, @RequestHeader Integer id) {
		if (userService.updateNickname(id, nickname)) {
			return RestResult.ok();
		}
		return RestResult.fail();
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
		String avatar = userService.updateAvatar(id, avatarFile);
		if (avatar != null) {
			return RestResult.ok(avatar);
		} else {
			return RestResult.fail("上传失败");
		}
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
			return RestResult.ok(result);
		}
		return RestResult.fail("修改失败");
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
			return RestResult.ok(result);
		}
		return RestResult.fail("修改失败");
	}

	/**
	 * 发送邮箱验证码
	 */
	@PostMapping("/mail/send-mail-verify")
	public RestResult<Object> sendMailVerifyForUpdateMail(@RequestHeader Integer id) {
		return userService.sendMailVerify(id);
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping("/user")
	public RestResult<Object> delete(@NotNull String password, @RequestHeader Integer id) {
		if (!userService.checkPassword(id, password)) {
			return new RestResult<>(false, "密码错误");
		} else {
			return new RestResult<>(userService.removeById(id));
		}
	}

}

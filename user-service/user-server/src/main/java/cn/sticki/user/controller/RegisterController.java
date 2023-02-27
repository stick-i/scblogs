package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.pojo.UserRegisterBO;
import cn.sticki.user.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 注册相关接口
 *
 * @author 阿杆
 */
@Slf4j
@RestController
@RequestMapping("/register")
@Validated
public class RegisterController {

	@Resource
	private RegisterService registerService;

	/**
	 * 发送邮箱验证码请求
	 * 60s内重复请求无效
	 *
	 * @param mail 邮箱
	 */
	@PostMapping("/send-mail-verify")
	public RestResult<Object> sendMailVerify(@NotNull String mail) {
		log.debug("mail->{}", mail);
		return registerService.sendMailVerify(mail);
	}

	/**
	 * 注册账号，必须有正确的验证码才能注册成功
	 */
	@PostMapping("/register")
	public RestResult<Object> register(UserRegisterBO userRegisterBO) {
		return registerService.register(userRegisterBO);
	}

}

package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.exception.UserException;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.service.RegisterService;
import cn.sticki.user.service.UserService;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

	@Resource
	private RegisterService registerService;

	@CreateCache(name = "user:register:sendMailTime:", expire = 300)
	private Cache<String, Long> cache;

	@Resource
	private UserService userService;

	/**
	 * 发送邮箱验证码请求
	 * 90s内重复请求无效
	 *
	 * @param mail 邮箱
	 */
	@PostMapping("/send-mail-verify")
	public RestResult<Object> sendMailVerify(String mail) {
		log.debug("mail->{}", mail);
		// todo 检查邮箱是否为合法邮箱
		Long sendTime = cache.get(mail);
		Long nowTime = System.currentTimeMillis() / 1000;
		// 判断是否发送过邮件，若上一次发送邮件的时间超过90s则允许发送
		if (sendTime == null || nowTime - sendTime > 90) {
			cache.put(mail, nowTime); // 将发送邮件的时间存到redis，先存时间，再发送
			registerService.sendMailVerify(mail);
			return new RestResult<>(true);
		}
		return new RestResult<>(400, "发送频繁");
	}

	/**
	 * 注册账号，必须有正确的验证码才能注册成功
	 *
	 * @param userSafety 用户注册时的账号密码等安全信息类
	 * @param mailVerify 接收到的验证码
	 */
	@PostMapping("/register")
	public RestResult<Object> register(UserSafety userSafety, @NotNull String mailVerify) {
		if (userService.getByUsername(userSafety.getUsername()) != null)
			throw new UserException("用户名已存在");
		if (!registerService.checkMailVerify(userSafety.getMail(), mailVerify)) {
			return new RestResult<>(false, "验证码错误");
		}
		return new RestResult<>(registerService.register(userSafety));
	}

}

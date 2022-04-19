package cn.sticki.blog.controller;

import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.vo.RestTemplate;
import cn.sticki.blog.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

	@Resource
	private RegisterService registerService;

	@Resource
	private HttpSession session;

	/**
	 * 发送邮箱验证码请求
	 * 90s内重复请求无效
	 *
	 * @param mail 邮箱
	 */
	@PostMapping("/send-mail-verify")
	public RestTemplate sendMailVerify(@NotNull String mail) throws Exception {
		log.debug("mail->{}", mail);
		// todo 改为存入redis，最好以包名+方法名来命名key
		Long sendTime = (Long) session.getAttribute("send-mail-verify-time");
		Long nowTime = System.currentTimeMillis() / 1000;
		// 判断是否发送过邮件，若上一次发送邮件的时间超过90s则允许发送
		if (sendTime == null || nowTime - sendTime > 90) {
			registerService.sendMailVerify(mail);
			session.setAttribute("send-mail-verify-time", nowTime); // 将发送邮件的时间存到session
			return new RestTemplate(true);
		}
		return new RestTemplate(false);
	}

	/**
	 * 注册账号，必须有正确的验证码才能注册成功
	 *
	 * @param userSafety 用户注册时的账号密码等安全信息类
	 * @param mailVerify 接收到的验证码
	 */
	@PostMapping("/register")
	public RestTemplate register(UserSafety userSafety, @NotNull String mailVerify) {
		if (!registerService.checkMailVerify(userSafety.getMail(), mailVerify)) {
			return new RestTemplate(false, "验证码错误");
		}
		return new RestTemplate(registerService.register(userSafety));
	}

}

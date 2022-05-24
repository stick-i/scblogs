package cn.sticki.user.controller;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.config.JwtConfig;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private PasswordEncoder encoder;

	@PostMapping("/login")
	public RestResult<Object> loginHandle(String username, String password, HttpServletResponse response) {
		LambdaQueryWrapper<UserSafety> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserSafety::getUsername, username);
		UserSafety user = userSafetyMapper.selectOne(wrapper);
		if (encoder.matches(password, user.getPassword())) {
			// 添加token
			response.setHeader(JwtConfig.headerName, JwtUtils.createToken("id", user.getUserId()));
			response.setHeader("Access-Control-Expose-Headers", JwtConfig.headerName);
			return new RestResult<>(user.getUsername());
		}
		return new RestResult<>(false, "用户名或密码错误");
	}

}

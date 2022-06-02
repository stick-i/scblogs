package cn.sticki.user.service.Impl;

import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.mapper.UserViewMapper;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private PasswordEncoder encoder;

	@Resource
	private UserViewMapper userViewMapper;

	@Override
	public UserView login(String username, String password) {
		LambdaQueryWrapper<UserSafety> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserSafety::getUsername, username);
		UserSafety user = userSafetyMapper.selectOne(wrapper);
		if (encoder.matches(password, user.getPassword())) {
			// 查询用户信息
			return userViewMapper.selectById(user.getUserId());
		}
		return null;
	}

}

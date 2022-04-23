package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.UserDetailsSecurity;
import cn.sticki.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserMapper userMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public User login(String username, String password) {
		// 丢给SpringSecurity走一遍，然后到自己定义的UserDetailsServiceImpl里面去查询账号密码，
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authenticate = authenticationManager.authenticate(token);
		if (Objects.isNull(authenticate)) {
			return null;
		}
		UserDetailsSecurity userDetails = (UserDetailsSecurity) authenticate.getPrincipal();
		UserSafety userSafety = userDetails.getUserSafety();
		return userMapper.selectById(userSafety.getUserId());
	}

}

package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Override
	public User login(String username, String password) {
		UserSafety userSafety = userSafetyMapper.selectByUsernameAndPassword(username, password);
		if (userSafety == null) return null;
		else return userMapper.selectById(userSafety.getUserId());
	}

	public User getByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

}

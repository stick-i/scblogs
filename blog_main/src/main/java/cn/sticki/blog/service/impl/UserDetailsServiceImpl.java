package cn.sticki.blog.service.impl;

import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.UserDetailsSecurity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据用户名查询用户信息
		LambdaQueryWrapper<UserSafety> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserSafety::getUsername, username);
		UserSafety userSafety = userSafetyMapper.selectOne(wrapper);
		// 如果查询不到数据就通过抛出异常来给出提示
		if (Objects.isNull(userSafety)) {
			log.debug("找不到用户");
			throw new UsernameNotFoundException("找不到用户");
		}
		log.debug("loadUserByUsername,userSafety->{}", userSafety);
		//TODO 根据用户查询权限信息 添加到UserDetailsImpl中

		// 封装在UserDetails对象返回
		return new UserDetailsSecurity(userSafety);
	}

}

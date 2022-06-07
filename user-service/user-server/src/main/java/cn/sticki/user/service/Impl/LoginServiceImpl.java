package cn.sticki.user.service.Impl;

import cn.sticki.common.result.RestResult;
import cn.sticki.resource.client.ResourceClient;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.mapper.UserViewMapper;
import cn.sticki.user.pojo.UserLoginBO;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

	@Resource
	private ResourceClient resourceClient;

	@Override
	public UserLoginBO login(String username, String password) {
		LambdaQueryWrapper<UserSafety> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserSafety::getUsername, username);
		UserSafety user = userSafetyMapper.selectOne(wrapper);
		if (encoder.matches(password, user.getPassword())) {
			// 查询用户信息
			UserView userView = userViewMapper.selectById(user.getUserId());
			// 查询院校名称
			UserLoginBO userLoginBO = new UserLoginBO();
			BeanUtils.copyProperties(userView, userLoginBO);
			RestResult<String> universityName = resourceClient.getUniversityName(userView.getSchoolCode());
			userLoginBO.setSchoolName(universityName.getData());
			return userLoginBO;
		}
		return null;
	}

}

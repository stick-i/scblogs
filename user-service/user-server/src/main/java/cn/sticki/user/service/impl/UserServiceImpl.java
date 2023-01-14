package cn.sticki.user.service.impl;

import cn.sticki.common.exception.BusinessException;
import cn.sticki.common.exception.MapperException;
import cn.sticki.common.result.RestResult;
import cn.sticki.common.tool.utils.RandomUtils;
import cn.sticki.resource.client.MessageClient;
import cn.sticki.resource.client.ResourceClient;
import cn.sticki.resource.client.pojo.MailDTO;
import cn.sticki.user.config.UserConfig;
import cn.sticki.user.mapper.UserGeneralMapper;
import cn.sticki.user.mapper.UserMapper;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.mapper.UserViewMapper;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserGeneral;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.sticki.user.utils.RedisConstants.*;

/**
 * @author 阿杆
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserViewMapper, UserView> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserGeneralMapper userGeneralMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private MessageClient messageClient;

	@Resource
	private ResourceClient resourceClient;

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Resource
	private RedisTemplate<String, Object> objectRedisTemplate;

	@Override
	public User getById(Integer id) {
		// 1. 构造redis key
		String key = USER_SERVICE_INFO_KEY + id;
		// 2. 尝试从redis获取
		User user = (User) objectRedisTemplate.opsForValue().get(key);
		if (user != null) {
			// 3. 存在，直接返回
			return user;
		}
		// 4. 不存在，查数据库
		user = super.getById(id);
		if (user != null) {
			// 4.1 查询成功，存入redis
			objectRedisTemplate.opsForValue().set(key, user, USER_SERVICE_INFO_TTL, TimeUnit.SECONDS);
		}
		// 5. 返回信息
		return user;
	}

	@Override
	public UserView getByUsername(String username) {
		return lambdaQuery().eq(UserView::getUsername, username).one();
	}

	@Override
	public Map<Integer, UserView> getUserListMap(Set<Integer> userIdList) {
		if (userIdList == null || userIdList.size() == 0) {
			return null;
		}
		List<UserView> userViewList = query().getBaseMapper().selectBatchIds(userIdList);
		HashMap<Integer, UserView> userMap = new HashMap<>(userIdList.size());
		for (UserView user : userViewList) {
			userMap.put(user.getId(), user);
		}
		return userMap;
	}

	@Override
	public Map<Integer, UserGeneral> getUserGeneralListMap(List<Integer> userIdList) {
		if (userIdList == null || userIdList.size() == 0) {
			return null;
		}
		LambdaQueryWrapper<UserGeneral> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(UserGeneral::getUserId, userIdList);
		List<UserGeneral> userGenerals = userGeneralMapper.selectList(wrapper);
		HashMap<Integer, UserGeneral> userGeneralMap = new HashMap<>(userIdList.size());
		for (UserGeneral userGeneral : userGenerals) {
			userGeneralMap.put(userGeneral.getUserId(), userGeneral);
		}
		return userGeneralMap;
	}

	@Override
	public boolean removeById(Integer id) {
		// 由于user表当中username字段为唯一索引，且该项目使用了逻辑删除
		// 所以我重写了userMapper的deleteById方法，将is_deleted字段值修改为id
		// user_safety也是一样
		if (userSafetyMapper.deleteById(id) == 1 && userMapper.deleteById(id) == 1) {
			return true;
		}
		throw new MapperException("删除失败", "remove by id error,id->" + id);
	}

	@Override
	public boolean checkPassword(Integer id, String password) {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		return passwordEncoder.matches(password, userSafety.getPassword());
	}

	@Override
	public boolean updatePasswordById(Integer id, String password) {
		String encode = passwordEncoder.encode(password);
		return userSafetyMapper.updatePasswordById(id, encode) > 0;
	}

	@Override
	public String updateAvatar(Integer id, MultipartFile avatarFile) {
		log.debug("updateAvatar,id->{}, fileName->{}", id, avatarFile.getOriginalFilename());
		// 默认头像才需要更新数据库，非默认头像无需更新数据库
		User user = userMapper.selectById(id);
		if (UserConfig.DefaultAvatar.equals(user.getAvatarUrl())) {
			// 拼接文件名的字符串，使用 userid+username 的格式来命名文件
			user.setAvatarUrl(user.getId() + "_" + user.getUsername());
			objectRedisTemplate.delete(USER_SERVICE_INFO_KEY + id);
			userMapper.updateById(user);
		}
		RestResult<String> result = resourceClient.uploadAvatarImage(avatarFile, user.getAvatarUrl());
		return result.getStatus() ? result.getData() : null;
	}

	@Override
	public boolean updateNickname(Integer id, String nickname) {
		User user = new User();
		user.setId(id);
		user.setNickname(nickname);
		objectRedisTemplate.delete(USER_SERVICE_INFO_KEY + id);
		return userMapper.updateById(user) == 1;
	}

	@Override
	public boolean updateSchoolCode(Integer id, Integer schoolCode) {
		User user = new User();
		user.setId(id);
		user.setSchoolCode(schoolCode);
		objectRedisTemplate.delete(USER_SERVICE_INFO_KEY + id);
		return userMapper.updateById(user) == 1;
	}

	@Override
	public boolean updateMail(Integer id, String mail) {
		objectRedisTemplate.delete(USER_SERVICE_INFO_KEY + id);
		return userSafetyMapper.updateMailById(id, mail) > 0;
	}

	@Override
	public boolean sendMailVerify(Integer id) {
		// 0. 构造redis key
		String key = USER_SERVICE_MAIL_CODE_KEY + id;
		// 1. 查询当前用户的最近发送记录，通过ttl判断
		Long expire = redisTemplate.getExpire(key);
		// 允许的发送时间间隔
		int sendCodeInterval = 60;
		if (expire != null && USER_SERVICE_MAIL_CODE_TTL - expire < sendCodeInterval) {
			// 1.1 若时间不为空且未超过固定的时间间隔，则不允许发送
			throw new BusinessException("发送频繁");
		}
		// 2 若为空或已经超过固定的时间间隔，则允许发送
		// 2.1 查询用户的邮箱
		UserSafety userSafety = userSafetyMapper.selectById(id);
		// 2.2 生成验证码，并构造发送邮件类型
		String code = RandomUtils.generator(6);
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(userSafety.getMail());
		mailDTO.setSubject("博客校园验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在操作你的账户信息，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		// 3. 将验证码保存到redis
		redisTemplate.opsForValue().set(key, code, USER_SERVICE_MAIL_CODE_TTL, TimeUnit.SECONDS);
		// 4. 发送邮件
		RestResult<Object> result = messageClient.sendMail(mailDTO);
		return result.getStatus();
	}

	@Override
	public boolean checkMailVerify(Integer id, @NotNull String verify) {
		String code = redisTemplate.opsForValue().get(USER_SERVICE_MAIL_CODE_KEY + id);
		return verify.equals(code);
	}

}

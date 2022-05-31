package cn.sticki.user.service.Impl;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.tool.utils.RandomUtils;
import cn.sticki.message.client.MessageClient;
import cn.sticki.message.pojo.MailDTO;
import cn.sticki.resource.client.ResourceClient;
import cn.sticki.user.config.UserConfig;
import cn.sticki.user.exception.SQLHandleException;
import cn.sticki.user.mapper.UserMapper;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.mapper.UserViewMapper;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.pojo.UserView;
import cn.sticki.user.service.UserService;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserViewMapper userViewMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private MessageClient messageClient;

	@Resource
	private ResourceClient resourceClient;

	@Override
	public UserView getById(Integer id) {
		return userViewMapper.selectById(id);
	}

	public UserView getByUsername(String username) {
		LambdaQueryWrapper<UserView> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserView::getUsername, username);
		return userViewMapper.selectOne(wrapper);
	}

	@Override
	public boolean removeById(Integer id) {
		if (userSafetyMapper.deleteById(id) + userMapper.deleteById(id) == 2)
			return true;
		throw new SQLHandleException("remove by id error,id->" + id);
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
	public boolean updateAvatar(Integer id, MultipartFile avatarFile) {
		log.debug("updateAvatar,id->{}, fileName->{}", id, avatarFile.getOriginalFilename());
		// 默认头像才需要更新数据库，非默认头像无需更新数据库
		User user = userMapper.selectById(id);
		if (UserConfig.DefaultAvatar.equals(user.getAvatarUrl())) {
			// 拼接文件名的字符串，使用 userid+username 的格式来命名文件
			user.setAvatarUrl(user.getId() + "_" + user.getUsername());
			userMapper.updateById(user);
		}
		RestResult<Boolean> result = resourceClient.uploadAvatarImage(avatarFile, user.getAvatarUrl());
		return result.getStatus();
	}

	@Override
	public boolean updateNickname(Integer id, String nickname) {
		User user = new User();
		user.setId(id);
		user.setNickname(nickname);
		return userMapper.updateById(user) == 1;
	}

	@Override
	public boolean updateSchoolCode(Integer id, Integer schoolCode) {
		User user = new User();
		user.setId(id);
		user.setSchoolCode(schoolCode);
		return userMapper.updateById(user) == 1;
	}

	@Override
	public boolean updateMail(Integer id, String mail) {
		return userSafetyMapper.updateMailById(id, mail) > 0;
	}

	// @CreateCache(name = CacheSpace.UserService_MailVerify, expire = 300)
	@CreateCache(name = "user:userService:mailVerifyCode", expire = 300)
	private Cache<String, String> mailCache;

	@Override
	public boolean sendMailVerify(Integer id) {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		String code = RandomUtils.generator(6, "0123456789");
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(userSafety.getMail());
		mailDTO.setSubject("博客校园验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在操作你的账户信息，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		mailCache.put(userSafety.getMail(), code);
		RestResult<Object> result = messageClient.sendMail(mailDTO);// 发送邮件
		return result.getStatus();
	}

	@Override
	public boolean checkMailVerify(Integer id, @NotNull String verify) {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		String code = mailCache.get(userSafety.getMail());
		if (verify.equals(code)) {
			mailCache.remove(userSafety.getMail());
			return true;
		}
		return false;
	}

}

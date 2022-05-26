package cn.sticki.user.service.Impl;

import cn.sticki.common.result.RestResult;
import cn.sticki.common.tool.utils.RandomUtils;
import cn.sticki.message.MessageClient;
import cn.sticki.message.pojo.MailDTO;
import cn.sticki.resource.ResourceClient;
import cn.sticki.user.config.UserConfig;
import cn.sticki.user.exception.SQLHandleException;
import cn.sticki.user.mapper.UserMapper;
import cn.sticki.user.mapper.UserSafetyMapper;
import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserSafety;
import cn.sticki.user.service.UserService;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
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
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private MessageClient messageClient;

	@Resource
	private ResourceClient resourceClient;

	@Override
	public User getById(Integer id) {
		return userMapper.selectById(id);
	}

	public User getByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	@Override
	public boolean removeById(Integer id) {
		if (userSafetyMapper.deleteById(id) + userMapper.deleteById(id) == 2)
			return true;
		throw new SQLHandleException("remove by id error,id->" + id);
	}

	@Override
	public boolean removeByUsername(String username) {
		if (userSafetyMapper.deleteByUsername(username) + userMapper.deleteByUsername(username) == 2)
			return true;
		throw new SQLHandleException("remove by username error,id->" + username);
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
		// 判断是否为默认头像，defaultAvatar中只有文件名，而user.getAvatar()为完整的链接
		// 默认头像才需要更新数据库，非默认头像无需更新数据库
		User user = userMapper.selectById(id);
		int index = user.getAvatarUrl().lastIndexOf("/") + 1; // 获取链接和文件名的分割点
		String fileName = user.getAvatarUrl().substring(index);
		String filePath = user.getAvatarUrl().substring(0, index);
		if (UserConfig.DefaultAvatar.equals(fileName)) {
			// 拼接文件名的字符串，使用 userid+username 的格式来命名文件
			String url = user.getId() + "_" + user.getUsername();
			userMapper.updateAvatarById(user.getId(), url);// 更新数据库
			fileName = url;
			user.setAvatarUrl(filePath + fileName);
		}
		log.debug("user.getAvatar() -> {}", fileName);
		RestResult<Boolean> result = resourceClient.uploadAvatarImage(avatarFile, fileName);
		return result.getStatus();
	}

	@Override
	public boolean updateNickname(Integer id, String nickname) {
		return userMapper.updateNicknameById(id, nickname) == 1;
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

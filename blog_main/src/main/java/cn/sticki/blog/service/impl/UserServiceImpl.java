package cn.sticki.blog.service.impl;

import cn.sticki.blog.enumeration.CacheSpace;
import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.MailDTO;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.FileUtils;
import cn.sticki.blog.util.MailUtils;
import cn.sticki.blog.util.OssUtils;
import cn.sticki.blog.util.RandomUtils;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private OssUtils ossUtils;

	@Resource
	private RandomUtils randomUtils;

	@Resource
	private MailUtils mailUtils;

	@Resource
	private FileUtils fileUtils;

	@Value("${minio.resource-path.avatar}")
	private String avatarPath;

	@Value("${resource.default-avatar}")
	private String defaultAvatar;

	@Resource
	private PasswordEncoder passwordEncoder;

	public User getByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	@Override
	public boolean removeById(Integer id) {
		// todo 需要加上事务
		return userSafetyMapper.deleteById(id) + userMapper.deleteById(id) == 2;
	}

	@Override
	public boolean removeByUsername(String username) {
		// todo 需要加上事务
		return userSafetyMapper.deleteByUsername(username) + userMapper.deleteByUsername(username) == 2;
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
	public void updateAvatar(User user, MultipartFile avatarFile) throws MinioException, IOException {
		log.debug("updateAvatar,username->{}, fileName->{}", user.getUsername(), avatarFile.getOriginalFilename());
		// 判断是否为默认头像
		if (defaultAvatar.equals(user.getAvatar())) {
			// 拼接文件名的字符串，使用 userid+username 的格式来命名文件
			String url = user.getId() + "_" + user.getUsername();
			userMapper.updateAvatarById(user.getId(), url);// 更新数据库
			user.setAvatar(url);
		}
		log.debug("avatarPath + user.getAvatar() -> {}", avatarPath + user.getAvatar());
		try (
				InputStream inputStream = avatarFile.getInputStream()
		) {
			// 上传新头像文件
			ossUtils.upload(
					avatarPath + user.getAvatar(),  // 对用户头像进行保存
					inputStream,
					avatarFile.getSize(),
					-1,
					avatarFile.getContentType()
			);
		}
	}

	@Override
	public boolean updateNickname(Integer id, String nickname) {
		return userMapper.updateNicknameById(id, nickname) == 1;
	}

	@Override
	public boolean updateMail(Integer id, String mail) {
		return userSafetyMapper.updateMailById(id, mail) > 0;
	}

	@CreateCache(name = CacheSpace.UserService_MailVerify, expire = 300)
	private Cache<String, String> mailCache;

	@Override
	public boolean sendMailVerify(Integer id) throws MailSendException {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		String code = randomUtils.generator(6, "0123456789");
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(userSafety.getMail());
		mailDTO.setSubject("博客校园验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在操作你的账户信息，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		mailCache.put(userSafety.getMail(), code);
		mailUtils.sendMail(mailDTO);  // 发送邮件
		return true;
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

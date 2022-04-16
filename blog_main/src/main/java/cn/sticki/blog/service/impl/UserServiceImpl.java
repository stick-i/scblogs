package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.pojo.dto.MailDTO;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.MailUtils;
import cn.sticki.blog.util.MinioUtils;
import cn.sticki.blog.util.RandomUtils;
import cn.sticki.blog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Service
// public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private MinioUtils minioUtils;

	@Resource
	private RandomUtils randomUtils;

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private MailUtils mailUtils;

	@Value("${minio.resource-path.avatar}")
	private String avatarPath;

	@Override
	public User login(String username, String password) {
		UserSafety userSafety = userSafetyMapper.selectByUsernameAndPassword(username, password);
		if (userSafety == null) return null;
		else return userMapper.selectById(userSafety.getUserId());
	}

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
		return userSafety.getPassword().equals(password);
	}

	@Override
	public boolean updatePasswordById(Integer id, String password) {
		return userSafetyMapper.updatePasswordById(id, password) > 0;
	}

	@Override
	public void updateAvatar(User user, MultipartFile avatarFile) throws MinioException, IOException {
		log.debug("updateAvatar,username->{}, fileName->{}", user.getUsername(), avatarFile.getOriginalFilename());
		// 拼接字符串，使用 uuid+username+文件后缀 的格式来命名文件
		String url = randomUtils.uuid() + "-" + user.getUsername() + getFileExtension(Objects.requireNonNull(avatarFile.getOriginalFilename()));
		userMapper.updateAvatarById(user.getId(), url); // 更新数据库
		minioUtils.removeFile(avatarPath + user.getAvatar()); // 删除原头像文件
		try (
				InputStream inputStream = avatarFile.getInputStream()
		) {
			// 上传新头像文件
			minioUtils.upload(
					avatarPath + url,  // 使用uuid+用户名的形式对用户头像进行保存
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

	@Override
	public boolean sendMailVerify(Integer id, String key) throws MailSendException {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		String code = randomUtils.generator(6, "0123456789");
		MailDTO mailDTO = new MailDTO();
		mailDTO.setFrom("博客校园");
		mailDTO.setTo(userSafety.getMail());
		mailDTO.setSubject("博客校园验证码");
		mailDTO.setText("亲爱的用户：\n" + "你正在操作你的账户信息，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");
		// todo key值要修改
		redisUtils.setex(key + userSafety.getMail(), 300, code);
		mailUtils.sendMail(mailDTO);  // 发送邮件
		return true;
	}

	@Override
	public boolean checkMailVerify(Integer id, @NotNull String verify, String key) {
		UserSafety userSafety = userSafetyMapper.selectById(id);
		String code = redisUtils.get(key + userSafety.getMail());
		System.out.println(key + userSafety.getMail());
		System.out.println(code);
		if (verify.equals(code)) {
			redisUtils.del(key + userSafety.getMail());
			return true;
		}
		return false;
	}

	private static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf("."));
		else return "";
	}

}

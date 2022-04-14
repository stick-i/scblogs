package cn.sticki.blog.service.impl;

import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.mapper.UserMapper;
import cn.sticki.blog.mapper.UserSafetyMapper;
import cn.sticki.blog.pojo.domain.User;
import cn.sticki.blog.pojo.domain.UserSafety;
import cn.sticki.blog.service.UserService;
import cn.sticki.blog.util.MinioUtils;
import cn.sticki.blog.util.RandomUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserSafetyMapper userSafetyMapper;

	@Resource
	private MinioUtils minioUtils;

	@Resource
	private RandomUtils randomUtils;

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
	public boolean removeByUsername(String username) {
		// todo 需要加上事务
		return userSafetyMapper.deleteByUsername(username) + userMapper.deleteByUsername(username) == 2;
	}

	@Override
	public void updateAvatar(User user, MultipartFile avatarFile) throws MinioException, IOException {
		log.debug("updateAvatar, username->{}, fileName->{}", user.getUsername(), avatarFile.getOriginalFilename());
		// 拼接字符串，使用 uuid+username+文件后缀 的格式来命名文件
		String url = randomUtils.uuid() + "-" + user.getUsername() + getFileExtension(Objects.requireNonNull(avatarFile.getOriginalFilename()));
		userMapper.updateAvatarById(user.getId(), url); // 更新数据库
		minioUtils.removeFile(avatarPath + user.getAvatar()); // 删除原头像文件
		// 上传新头像文件
		minioUtils.upload(
				avatarPath + url,  // 使用uuid+用户名的形式对用户头像进行保存
				avatarFile.getInputStream(),
				avatarFile.getSize(),
				-1,
				avatarFile.getContentType()
		);
	}

	@Override
	public boolean updateNickname(Integer id, String nickname) {
		return userMapper.updateNicknameById(id, nickname) == 1;
	}

	private static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf("."));
		else return "";
	}

}

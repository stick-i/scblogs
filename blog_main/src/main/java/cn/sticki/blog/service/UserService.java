package cn.sticki.blog.service;

import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService extends IService<User> {

	/**
	 * 用户登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	User login(String username, String password);

	User getByUsername(String username);

	/**
	 * 移除用户
	 *
	 * @param username 用户名
	 */
	boolean removeByUsername(String username);

	/**
	 * 更新用户头像
	 *
	 * @param user       用户信息
	 * @param avatarFile 头像文件
	 * @throws MinioException minio服务器异常
	 * @throws IOException    文件为空
	 */
	void updateAvatar(User user, MultipartFile avatarFile) throws MinioException, IOException;

	/**
	 * 更新用户昵称
	 *
	 * @param id       用户id
	 * @param nickname 用户昵称
	 */
	boolean updateNickname(Integer id, String nickname);

}

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

	void updateAvatar(User user, MultipartFile avatarFile) throws MinioException, IOException;

}

package cn.sticki.blog.service;

import cn.sticki.blog.exception.systemException.MailSendException;
import cn.sticki.blog.exception.systemException.MinioException;
import cn.sticki.blog.pojo.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// public interface UserService extends IService<User> {
public interface UserService {

	User getByUsername(String username);

	/**
	 * 移除用户
	 *
	 * @param id 用户id
	 */
	boolean removeById(Integer id);

	/**
	 * 移除用户
	 *
	 * @param username 用户名
	 */
	boolean removeByUsername(String username);

	/**
	 * 检查密码是否正确
	 */
	boolean checkPassword(Integer id, String password);

	/**
	 * 修改密码
	 *
	 * @param id       用户id
	 * @param password 新密码
	 */
	boolean updatePasswordById(Integer id, String password);

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

	/**
	 * 更新用户邮箱
	 */
	boolean updateMail(Integer id, String mail);

	/**
	 * 发送邮箱验证码
	 */
	boolean sendMailVerify(Integer id) throws MailSendException;

	/**
	 * 检查验证码
	 *
	 * @param id     用户id
	 * @param verify 验证码
	 */
	boolean checkMailVerify(Integer id, String verify);

}

package cn.sticki.user.service;

import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserView;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

public interface UserService extends IService<UserView> {

	User getById(Integer id);

	User getByUsername(String username);

	/**
	 * 批量获取用户信息，并封装成key为userId,value为user的map
	 *
	 * @param userIdList 用户id列表
	 */
	Map<Integer, UserView> getUserListMap(Set<Integer> userIdList);

	/**
	 * 移除用户
	 *
	 * @param id 用户id
	 */
	boolean removeById(Integer id);

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
	 * @param id         用户id
	 * @param avatarFile 头像文件
	 */
	String updateAvatar(Integer id, MultipartFile avatarFile);

	/**
	 * 更新用户昵称
	 *
	 * @param id       用户id
	 * @param nickname 用户昵称
	 */
	boolean updateNickname(Integer id, String nickname);

	/**
	 * 更新用户的院校代码
	 *
	 * @param id         用户id
	 * @param schoolCode 院校代码
	 */
	boolean updateSchoolCode(Integer id, Integer schoolCode);

	/**
	 * 更新用户邮箱
	 */
	boolean updateMail(Integer id, String mail);

	/**
	 * 发送邮箱验证码
	 */
	boolean sendMailVerify(Integer id);

	/**
	 * 检查验证码
	 *
	 * @param id     用户id
	 * @param verify 验证码
	 */
	boolean checkMailVerify(Integer id, String verify);

}

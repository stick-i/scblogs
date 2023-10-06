package cn.sticki.user.service;

import cn.sticki.user.pojo.User;
import cn.sticki.user.pojo.UserGeneral;
import cn.sticki.user.pojo.UserView;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 阿杆
 */
public interface UserService extends IService<UserView> {

	/**
	 * 获取用户信息
	 *
	 * @param id 用户id
	 * @return 用户信息
	 */
	User getById(Integer id);

	/**
	 * 获取用户信息
	 *
	 * @param username 用户名
	 * @return 用户信息
	 */
	User getByUsername(String username);

	/**
	 * 批量获取用户信息，并封装成key为userId,value为user的map
	 *
	 * @param userIdList 用户id列表
	 * @return 用户信息map，key为用户id，value为用户信息
	 */
	Map<Integer, UserView> getUserListMap(Set<Integer> userIdList);

	/**
	 * 获取用户创作信息
	 *
	 * @param userId 用户id
	 * @return 批量用户统计数据
	 */
	UserGeneral getUserGeneral(Integer userId);

	/**
	 * 批量查询用户各项数据统计
	 *
	 * @param userIdList 用户id列表
	 * @return 批量用户统计数据
	 */
	Map<Integer, UserGeneral> getUserGeneralListMap(List<Integer> userIdList);

	/**
	 * 移除用户
	 *
	 * @param id 用户id
	 * @return 是否删除成功
	 */
	boolean removeById(Integer id);

	/**
	 * 检查密码是否正确
	 *
	 * @param id       用户id
	 * @param password 密码
	 * @return 是否验证成功
	 */
	boolean checkPassword(Integer id, String password);

	/**
	 * 修改密码
	 *
	 * @param id       用户id
	 * @param password 新密码
	 * @return 是否修改成功
	 */
	boolean updatePasswordById(Integer id, String password);

	/**
	 * 更新用户头像
	 *
	 * @param id         用户id
	 * @param avatarFile 头像文件
	 * @return 资源链接
	 */
	String updateAvatar(Integer id, MultipartFile avatarFile);

	/**
	 * 更新用户昵称
	 *
	 * @param id       用户id
	 * @param nickname 用户昵称
	 * @return 是否更新成功
	 */
	boolean updateNickname(Integer id, String nickname);

	/**
	 * 更新用户的院校代码
	 *
	 * @param id         用户id
	 * @param schoolCode 院校代码
	 * @return 是否更新成功
	 */
	boolean updateSchoolCode(Integer id, Integer schoolCode);

	/**
	 * 更新用户邮箱
	 *
	 * @param id   用户id
	 * @param mail 新邮箱
	 * @return 是否更新成功
	 */
	boolean updateMail(Integer id, String mail);

	/**
	 * 发送邮箱验证码
	 *
	 * @param id 用户id
	 * @return 是否发送成功
	 */
	boolean sendMailVerify(Integer id);

	/**
	 * 检查验证码
	 *
	 * @param id     用户id
	 * @param verify 验证码
	 * @return 是否验证成功
	 */
	boolean checkMailVerify(Integer id, String verify);

}

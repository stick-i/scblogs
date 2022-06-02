package cn.sticki.user.service;

import cn.sticki.user.pojo.UserRegisterBO;
import cn.sticki.user.pojo.UserSafety;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RegisterService extends IService<UserSafety> {

	/**
	 * 用户注册
	 *
	 * @return 返回一个bool值，表示成功或失败
	 */
	boolean register(UserRegisterBO userRegisterBO);

	/**
	 * 发送邮件验证码，若该邮箱已经被其他账号绑定，则返回null
	 *
	 * @param mailAddress 邮箱地址
	 */
	void sendMailVerify(String mailAddress);

	/**
	 * 验证码检查
	 *
	 * @param mailAddress 邮件地址
	 * @param code        待核对的验证码
	 * @return 验证结果，成功或失败
	 */
	boolean checkMailVerify(String mailAddress, String code);

}

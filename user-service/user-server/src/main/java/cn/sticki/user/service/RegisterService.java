package cn.sticki.user.service;

import cn.sticki.common.result.RestResult;
import cn.sticki.user.pojo.UserRegisterBO;
import cn.sticki.user.pojo.UserSafety;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 阿杆
 */
public interface RegisterService extends IService<UserSafety> {

	/**
	 * 用户注册
	 *
	 * @param userRegisterBO 用户注册信息
	 * @return 返回一个bool值，表示成功或失败
	 */
	RestResult<Object> register(UserRegisterBO userRegisterBO);

	/**
	 * 发送邮件验证码，若该邮箱已经被其他账号绑定，则返回null
	 *
	 * @param mailAddress 邮箱地址
	 * @return 返回发送结果
	 */
	RestResult<Object> sendMailVerify(String mailAddress);

}

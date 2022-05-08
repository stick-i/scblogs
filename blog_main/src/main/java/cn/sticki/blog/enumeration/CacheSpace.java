package cn.sticki.blog.enumeration;

public class CacheSpace {

	/**
	 * 用户登录信息
	 */
	public static final String Login_UserID = "login:userId:";

	/**
	 * 注册邮箱验证码发送时间
	 */
	public static final String Register_SendMailTime = "register:sendMailTime:";

	/**
	 * 注册邮箱验证码
	 */
	public static final String Register_MailVerify = "register:mailVerify:";

	/**
	 * UserService邮箱验证码发送时间
	 */
	public static final String UserService_SendMailTime = "userService:sendMailTime:";

	/**
	 * UserService邮箱验证码
	 */
	public static final String UserService_MailVerify = "userService:mailVerify:";

	/**
	 * ip 计数
	 */
	public static final String IpService_Count = "ipService:Count:";

}

package cn.sticki.common.regex;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/16 11:00
 */
public class RegexPatterns {

	/**
	 * 手机号正则
	 */
	public static final String PHONE_REGEX = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

	/**
	 * 邮箱正则
	 */
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

	/**
	 * 密码正则。5-20位的字母、数字、下划线、特殊符号
	 */
	public static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*?_-]{5,20}$";

	/**
	 * 验证码正则, 6位数字或字母
	 */
	public static final String VERIFY_CODE_REGEX = "^[a-zA-Z\\d]{6}$";

}

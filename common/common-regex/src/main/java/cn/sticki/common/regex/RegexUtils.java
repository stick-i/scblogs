package cn.sticki.common.regex;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/16 11:00
 */
public class RegexUtils {

	/**
	 * 是否是有效手机格式
	 *
	 * @param phone 要校验的手机号
	 * @return true:符合，false：不符合
	 */
	public static boolean isPhoneInvalid(String phone) {
		return match(phone, RegexPatterns.PHONE_REGEX);
	}

	/**
	 * 是否是有效邮箱格式
	 *
	 * @param email 要校验的邮箱
	 * @return true:有效，false：无效
	 */
	public static boolean isEmailInvalid(String email) {
		return match(email, RegexPatterns.EMAIL_REGEX);
	}

	/**
	 * 是否是有效验证码格式
	 *
	 * @param code 要校验的验证码
	 * @return true:有效，false：无效
	 */
	public static boolean isCodeInvalid(String code) {
		return match(code, RegexPatterns.VERIFY_CODE_REGEX);
	}

	/**
	 * 校验是否 <b>符合</b> 正则格式
	 *
	 * @param str   待校验的字符串
	 * @param regex 正则表达式
	 * @return true:符合,false:不符合
	 */
	private static boolean match(String str, String regex) {
		boolean isBlank = str == null || str.isEmpty();
		if (isBlank) {
			return false;
		}
		return str.matches(regex);
	}

}

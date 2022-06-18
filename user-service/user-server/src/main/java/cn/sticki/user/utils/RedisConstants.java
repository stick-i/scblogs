package cn.sticki.user.utils;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/16 10:44
 */
public class RedisConstants {

	public static final String REGISTER_MAIL_CODE_KEY = "user:register:mailCode:";

	public static final Long REGISTER_MAIL_CODE_TTL = 300L;

	public static final String USER_SERVICE_MAIL_CODE_KEY = "user:userService:mailCode:";

	public static final Long USER_SERVICE_MAIL_CODE_TTL = 300L;

}

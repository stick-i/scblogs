package cn.sticki.common.web.auth;

import cn.sticki.common.web.exception.UnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取用户登录的账号信息
 * <p>
 * 该类与网关服务耦合，所获取的用户信息是由 gateway-service 解析而来的
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/11/30 11:53
 */
public class AuthHelper {

	public static final String USER_ID_HEADER = "user-id";

	/**
	 * 获取当前用户的id
	 *
	 * @return 当前用户id，当用户未登录时为null
	 */
	@Nullable
	public static Integer getCurrentUserId() {
		// 获取当前线程的请求
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attribute == null) {
			return null;
		}
		// 获取请求中的用户信息
		String header = attribute.getRequest().getHeader(USER_ID_HEADER);
		if (header == null) {
			return null;
		}
		Integer userId;
		try {
			userId = Integer.valueOf(header);
		} catch (NumberFormatException ignored) {
			// 解析失败
			userId = null;
		}
		return userId;
	}

	/**
	 * 获取当前用户的id 或 抛出异常
	 *
	 * @return 当前用户id，一定不为null
	 * @throws UnauthorizedException 若未登录
	 */
	@NotNull
	public static Integer getCurrentUserIdOrExit() {
		Integer currentUserId = getCurrentUserId();
		if (currentUserId == null) {
			throw new UnauthorizedException();
		}
		return currentUserId;
	}

}

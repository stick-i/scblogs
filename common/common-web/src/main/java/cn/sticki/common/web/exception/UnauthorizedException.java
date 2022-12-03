package cn.sticki.common.web.exception;

import cn.sticki.common.exception.BusinessException;

/**
 * 用户未登录异常
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/11/27 16:05
 */
public class UnauthorizedException extends BusinessException {

	public UnauthorizedException() {
		super("用户未登录");
	}

}

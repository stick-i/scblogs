package cn.sticki.common.exception.userException;

import cn.sticki.common.exception.UserException;

public class NotLoginException extends UserException {

	public NotLoginException(String errorMessage) {
		super(errorMessage);
	}

	public NotLoginException() {
		super("用户未登录");
	}

}

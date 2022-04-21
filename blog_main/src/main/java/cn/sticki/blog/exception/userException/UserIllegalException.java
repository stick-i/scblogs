package cn.sticki.blog.exception.userException;

import cn.sticki.blog.exception.UserException;

public class UserIllegalException extends UserException {

	public UserIllegalException(String errorMessage) {
		super(errorMessage);
	}

	public UserIllegalException() {
		super("非法操作");
	}

}

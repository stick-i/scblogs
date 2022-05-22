package cn.sticki.common.exception.userException;

import cn.sticki.common.exception.UserException;

public class UserIllegalException extends UserException {

	public UserIllegalException(String errorMessage) {
		super(errorMessage);
	}

	public UserIllegalException() {
		super("非法操作");
	}

}

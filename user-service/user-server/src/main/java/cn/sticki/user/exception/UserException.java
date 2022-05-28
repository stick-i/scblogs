package cn.sticki.user.exception;

import cn.sticki.common.exception.ServiceException;

public class UserException extends ServiceException {

	public UserException(String errorMessage) {
		super(errorMessage);
	}

}

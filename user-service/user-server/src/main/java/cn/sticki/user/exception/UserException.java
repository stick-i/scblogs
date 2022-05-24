package cn.sticki.user.exception;

import cn.sticki.common.tool.exception.ServiceException;

public class UserException extends ServiceException {

	public UserException(String errorMessage) {
		super(errorMessage);
	}

}

package cn.sticki.user.exception;

import cn.sticki.common.exception.ServiceException;

/**
 * @author 阿杆
 */
public class UserException extends ServiceException {

	public UserException(String errorMessage) {
		super(errorMessage);
	}

}

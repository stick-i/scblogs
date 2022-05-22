package cn.sticki.common.exception.userException;

import cn.sticki.common.exception.UserException;

public class SqlLimitException extends UserException {

	public SqlLimitException(String errorMessage) {
		super(errorMessage);
	}

	public SqlLimitException() {
		super("over limit");
	}

}

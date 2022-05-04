package cn.sticki.blog.exception.userException;

import cn.sticki.blog.exception.UserException;

public class SqlLimitException extends UserException {

	public SqlLimitException(String errorMessage) {
		super(errorMessage);
	}

	public SqlLimitException() {
		super("over limit");
	}

}

package cn.sticki.blog.exception.systemException;

import cn.sticki.blog.exception.SystemException;

public class DAOException extends SystemException {

	public DAOException() {
		super("数据库异常");
	}

	public DAOException(String errorMessage) {
		super(errorMessage);
	}

}

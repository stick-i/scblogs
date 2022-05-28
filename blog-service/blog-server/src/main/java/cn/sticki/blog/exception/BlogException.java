package cn.sticki.blog.exception;

import cn.sticki.common.exception.ServiceException;

public class BlogException extends ServiceException {

	public BlogException() {
		super("blog service error");
	}

	public BlogException(String errorMessage) {
		super(errorMessage);
	}

}

package cn.sticki.blog.exception;

import cn.sticki.common.tool.exception.BaseBusinessException;

public class BlogException extends BaseBusinessException {

	public BlogException() {
		super("blog service error");
	}

	public BlogException(String errorMessage) {
		super(errorMessage);
	}

}

package cn.sticki.blog.exception;

import cn.sticki.common.tool.exception.MapperException;

public class BlogMapperException extends MapperException {

	public BlogMapperException() {
		super("blog mapper error");
	}

	public BlogMapperException(String errorMessage) {
		super(errorMessage);
	}

}

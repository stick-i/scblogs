package cn.sticki.blog.exception;

import cn.sticki.common.exception.MapperException;

/**
 * @author 阿杆
 */
public class BlogMapperException extends MapperException {

	public BlogMapperException() {
		super("blog mapper error");
	}

	public BlogMapperException(String errorMessage) {
		super(errorMessage);
	}

}

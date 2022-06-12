package cn.sticki.blog.exception;

import cn.sticki.common.exception.ServiceException;

/**
 * @author 阿杆
 */
public class BlogException extends ServiceException {

	public BlogException() {
		super("blog service error");
	}

	public BlogException(String errorMessage) {
		super(errorMessage);
	}

}

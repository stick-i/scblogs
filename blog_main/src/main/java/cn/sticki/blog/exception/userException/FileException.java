package cn.sticki.blog.exception.userException;

import cn.sticki.blog.exception.UserException;

public class FileException extends UserException {

	public FileException() {
		super("文件异常");
	}

	public FileException(String errorMessage) {
		super(errorMessage);
	}

}

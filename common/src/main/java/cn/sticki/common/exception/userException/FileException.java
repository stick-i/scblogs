package cn.sticki.common.exception.userException;

import cn.sticki.common.exception.UserException;

public class FileException extends UserException {

	public FileException() {
		super("文件异常");
	}

	public FileException(String errorMessage) {
		super(errorMessage);
	}

}

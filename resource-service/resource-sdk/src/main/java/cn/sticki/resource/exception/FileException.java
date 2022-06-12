package cn.sticki.resource.exception;

import cn.sticki.common.exception.ServiceException;

/**
 * @author 阿杆
 */
public class FileException extends ServiceException {

	public FileException() {
		super("文件异常");
	}

	public FileException(String errorMessage) {
		super(errorMessage);
	}

}

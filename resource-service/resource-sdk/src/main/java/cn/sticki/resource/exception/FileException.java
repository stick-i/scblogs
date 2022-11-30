package cn.sticki.resource.exception;

import cn.sticki.common.exception.BusinessException;

/**
 * @author 阿杆
 */
public class FileException extends BusinessException {

	public FileException() {
		super("文件异常");
	}

	public FileException(String errorMessage) {
		super(errorMessage);
	}

}

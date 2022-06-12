package cn.sticki.resource.exception;

import cn.sticki.common.exception.ServiceException;

/**
 * @author 阿杆
 */
public class UploadException extends ServiceException {

	public UploadException(String errorMessage) {
		super(errorMessage);
	}

}

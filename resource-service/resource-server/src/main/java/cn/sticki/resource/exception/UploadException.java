package cn.sticki.resource.exception;

import cn.sticki.common.exception.BusinessException;

/**
 * @author 阿杆
 */
public class UploadException extends BusinessException {

	public UploadException(String errorMessage) {
		super(errorMessage);
	}

}

package cn.sticki.blink.exception;

import cn.sticki.common.exception.ServiceException;

public class BlinkException extends ServiceException {

	public BlinkException() {
		super("操作异常");
	}

	public BlinkException(String errorMessage) {
		super(errorMessage);
	}

}

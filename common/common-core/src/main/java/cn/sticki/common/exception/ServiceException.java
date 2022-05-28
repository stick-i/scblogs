package cn.sticki.common.exception;

/**
 * 业务层异常
 */
public class ServiceException extends BaseBusinessException {

	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

}

package cn.sticki.common.exception;

/**
 * 所有异常的基类
 *
 * @author 阿杆
 */
public class BaseBusinessException extends RuntimeException {

	public BaseBusinessException() {
		super("system error");
	}

	public BaseBusinessException(String errorMessage) {
		super(errorMessage);
	}

}

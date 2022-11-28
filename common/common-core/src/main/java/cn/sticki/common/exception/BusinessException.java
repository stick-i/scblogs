package cn.sticki.common.exception;

/**
 * 普通业务异常，一般是由用户操作导致的，不需要打印日志
 * <p>
 * 如，密码错误、用户不存在等
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/11/27 16:08
 */
public class BusinessException extends BaseException {

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message, 400);
	}

	public BusinessException(String message, Integer code) {
		super(message, code);
	}

	public BusinessException(String message, Integer code, Throwable cause) {
		super(message, code, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}

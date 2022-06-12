package cn.sticki.user.exception;

/**
 * @author 阿杆
 */
public class UserIllegalException extends UserException {

	public UserIllegalException(String errorMessage) {
		super(errorMessage);
	}

	public UserIllegalException() {
		super("非法操作");
	}

}

package cn.sticki.user.exception;

public class UserIllegalException extends UserException {

	public UserIllegalException(String errorMessage) {
		super(errorMessage);
	}

	public UserIllegalException() {
		super("非法操作");
	}

}

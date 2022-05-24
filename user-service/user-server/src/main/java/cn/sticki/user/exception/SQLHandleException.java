package cn.sticki.user.exception;

public class SQLHandleException extends RuntimeException {

	public SQLHandleException(String message) {
		super(message);
	}

	public SQLHandleException() {
		super("数据库操作异常");
	}

}

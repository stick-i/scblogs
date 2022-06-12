package cn.sticki.user.exception;

/**
 * @author 阿杆
 */
public class SqlHandleException extends RuntimeException {

	public SqlHandleException(String message) {
		super(message);
	}

	public SqlHandleException() {
		super("数据库操作异常");
	}

}

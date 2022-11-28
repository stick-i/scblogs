package cn.sticki.common.exception;

/**
 * 微服务异常基类，该基类继承自RuntimeException
 * <p>
 * 模仿java的异常类写的，并在其基础上加了一个字段 {@link BaseException#errorCode}
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/11/27 16:08
 */
public class BaseException extends RuntimeException {

	/**
	 * 关于该异常的错误代码
	 */
	private int errorCode = 500;

	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public BaseException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public BaseException(String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public BaseException(String message, Integer code) {
		super(message);
		this.errorCode = code;
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this runtime exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 * @since 1.4
	 */
	public BaseException(String message, Integer code, Throwable cause) {
		super(message, cause);
		this.errorCode = code;
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a
	 * detail message of <tt>(cause==null ? null : cause.toString())</tt>
	 * (which typically contains the class and detail message of
	 * <tt>cause</tt>).  This constructor is useful for runtime exceptions
	 * that are little more than wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 * @since 1.4
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 返回此异常的错误码。
	 *
	 * @return 此异常的错误码（不为null）
	 */
	public int getCode() {
		return errorCode;
	}

}

package cn.sticki.resource.exception;

public class FileNullException extends FileException {

	public FileNullException() {
		super("文件为null");
	}

	public FileNullException(String errorMessage) {
		super(errorMessage);
	}

}

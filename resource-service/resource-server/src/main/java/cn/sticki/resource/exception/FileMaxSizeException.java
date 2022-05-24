package cn.sticki.resource.exception;

public class FileMaxSizeException extends FileException {

	public FileMaxSizeException() {
		super("文件过大");
	}

	public FileMaxSizeException(String errorMessage) {
		super(errorMessage);
	}

}

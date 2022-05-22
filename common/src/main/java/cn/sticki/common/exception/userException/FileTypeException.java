package cn.sticki.common.exception.userException;

public class FileTypeException extends FileException {

	public FileTypeException() {
		super("不支持的文件类型");
	}

	public FileTypeException(String errorMessage) {
		super(errorMessage);
	}

}

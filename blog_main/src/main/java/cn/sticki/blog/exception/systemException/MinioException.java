package cn.sticki.blog.exception.systemException;

import cn.sticki.blog.exception.SystemException;

public class MinioException extends SystemException {

	public MinioException(String errorMessage) {
		super(errorMessage);
	}

	public MinioException() {
		super("minio系统异常");
	}

}

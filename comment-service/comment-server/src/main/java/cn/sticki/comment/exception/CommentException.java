package cn.sticki.comment.exception;

import cn.sticki.common.exception.ServiceException;

public class CommentException extends ServiceException {

	public CommentException(String errorMessage) {
		super(errorMessage);
	}

	public CommentException() {
		super("评论异常");
	}

}

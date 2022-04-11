package cn.sticki.blog.exception.systemException;

import cn.sticki.blog.exception.SystemException;

public class MailSendException extends SystemException {

	public MailSendException(String errorMessage) {
		super(errorMessage);
	}

	public MailSendException() {
		super("邮件发送失败");
	}

}

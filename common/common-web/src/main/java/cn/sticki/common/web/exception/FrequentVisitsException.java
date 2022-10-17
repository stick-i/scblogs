package cn.sticki.common.web.exception;

import cn.sticki.common.exception.ServiceException;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/10/14 22:19
 */
public class FrequentVisitsException extends ServiceException {

	public FrequentVisitsException(String errorMessage) {
		super(errorMessage);
	}

	public FrequentVisitsException() {
		super("访问频繁");
	}

}

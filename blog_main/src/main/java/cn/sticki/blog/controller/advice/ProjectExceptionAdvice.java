package cn.sticki.blog.controller.advice;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.pojo.vo.RestTemplate;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 作为SpringMVC的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {

	// 拦截所有的信息
	@ExceptionHandler(Exception.class)
	public RestTemplate doException(Exception e) {
		// 记录日志
		// 通知运维
		// 通知开发
		e.printStackTrace();
		return new RestTemplate(500, "服务器故障，请稍后再试", null, false);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public RestTemplate doMissingPathVariableException(MissingPathVariableException e) {
		e.printStackTrace();
		return new RestTemplate(400, "数据异常", null, false);
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public RestTemplate doHttpMessageConversionException(HttpMessageConversionException e) {
		e.printStackTrace();
		return new RestTemplate(400, "数据异常", null, false);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public RestTemplate doHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		e.printStackTrace();
		return new RestTemplate(400, "请求方式异常", null, false);
	}

	@ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
	public RestTemplate doIllegalArgumentException(RuntimeException e) {
		e.printStackTrace();
		return new RestTemplate(400, "参数异常", null, false);
	}

	@ExceptionHandler(UserException.class)
	public RestTemplate doUserException(UserException e) {
		e.printStackTrace();
		return new RestTemplate(400, e.getMessage(), null, false);
	}

}

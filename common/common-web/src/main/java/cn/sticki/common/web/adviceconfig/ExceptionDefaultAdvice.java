package cn.sticki.common.web.adviceconfig;

import cn.sticki.common.exception.BaseBusinessException;
import cn.sticki.common.exception.ServiceException;
import cn.sticki.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.net.ConnectException;

/**
 * 默认的SpringMVC的异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ExceptionDefaultAdvice {

	// 拦截所有的信息
	@ExceptionHandler(Exception.class)
	public RestResult<Object> doException(Exception e) {
		e.printStackTrace();
		return new RestResult<>(500, "服务器故障，请稍后再试", null, false);
	}

	@ExceptionHandler(ConnectException.class)
	public RestResult<Object> doConnectException(ConnectException e) {
		log.warn("服务连接失败,{}", e.getMessage());
		return new RestResult<>(501, "服务器连接故障，请联系管理员", null, false);
	}

	@ExceptionHandler(BaseBusinessException.class)
	public RestResult<Object> doBaseBusinessException(BaseBusinessException e) {
		log.warn(e.getMessage());
		return new RestResult<>(400, "操作异常，请稍后再试", null, false);
	}

	@ExceptionHandler(ServiceException.class)
	public RestResult<Object> doServiceException(ServiceException e) {
		log.warn(e.getMessage());
		return new RestResult<>(401, e.getMessage(), null, false);
	}

	@ExceptionHandler(ServletException.class)
	public RestResult<Object> doServletException(Exception e) {
		log.warn("请求异常,{}", e.getMessage());
		return new RestResult<>(402, "请求异常", null, false);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
	public RestResult<Object> doHttpRequestMethodNotSupportedException(Exception e) {
		log.warn("请求方式异常,{}", e.getMessage());
		return new RestResult<>(402, "请求方式异常", null, false);
	}

	@ExceptionHandler({MissingRequestValueException.class, IllegalArgumentException.class, NullPointerException.class, TypeMismatchException.class, BindException.class})
	public RestResult<Object> doIllegalArgumentException(Exception e) {
		log.warn("参数异常,{}", e.getMessage());
		return new RestResult<>(402, "参数异常", null, false);
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public RestResult<Object> doHttpMessageConversionException(HttpMessageConversionException e) {
		log.warn("数据异常,{}", e.getMessage());
		return new RestResult<>(403, "数据异常", null, false);
	}

	// 访问不存在的页面
	@ExceptionHandler(NoHandlerFoundException.class)
	public RestResult<Object> doNoHandlerFoundException(Exception e) {
		log.warn("页面不存在,{}", e.getMessage());
		return new RestResult<>(404, "操作异常", null, false);
	}

}

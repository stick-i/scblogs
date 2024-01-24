package cn.sticki.common.web.advice;

import cn.sticki.common.exception.BusinessException;
import cn.sticki.common.exception.MapperException;
import cn.sticki.common.exception.SystemException;
import cn.sticki.common.result.RestResult;
import jakarta.servlet.ServletException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.util.List;

/**
 * 默认的SpringMVC的异常处理器
 *
 * @author 阿杆
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionAdvice {

	static final String SYSTEM_ERROR_INFO = "服务器故障，请稍后再试";

	private RestResult<Object> fail(Integer code, String message) {
		return RestResult.fail(message, code);
	}

	/**
	 * 异常信息兜底
	 */
	@ExceptionHandler(Exception.class)
	public Object doException(Exception e) {
		log.error("系统故障：{}", e.getMessage());
		e.printStackTrace();
		return fail(500, SYSTEM_ERROR_INFO);
	}

	/**
	 * 由开发者记录的系统异常
	 */
	@ExceptionHandler(SystemException.class)
	public Object doSystemException(SystemException e) {
		log.warn("系统异常：{}", e.getMessage());
		return fail(e.getCode(), SYSTEM_ERROR_INFO);
	}

	/**
	 * 数据库相关异常，打印日志时会打印出数据信息，但不会返回给用户
	 */
	@ExceptionHandler(MapperException.class)
	public Object doMapperException(MapperException e) {
		log.warn("数据库异常,{}: {}", e.getMessage(), e.getData());
		return fail(e.getCode(), e.getMessage());
	}

	/**
	 * 普通业务异常，无需打印日志
	 */
	@ExceptionHandler(BusinessException.class)
	public Object doBusinessException(BusinessException e) {
		return fail(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(ConnectException.class)
	public Object doConnectException(ConnectException e) {
		log.warn("服务连接失败,{}", e.getMessage());
		return fail(501, "服务器故障，请联系管理员");
	}

	@ExceptionHandler(ServletException.class)
	public Object doServletException(Exception e) {
		log.info("请求异常,{}", e.getMessage());
		return fail(402, "请求异常");
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
	public Object doHttpRequestMethodNotSupportedException(Exception e) {
		log.info("请求方式异常,{}", e.getMessage());
		return fail(402, "请求方式异常");
	}

	@ExceptionHandler({MissingRequestValueException.class, IllegalArgumentException.class, TypeMismatchException.class, ValidationException.class})
	public Object doIllegalArgumentException(Exception e) {
		log.info("参数异常,{}", e.getMessage());
		return fail(402, "参数异常");
	}

	@ExceptionHandler(BindException.class)
	public Object doBindException(BindException e) {
		List<FieldError> allErrors = e.getFieldErrors();
		StringBuilder info = new StringBuilder("数据异常:");
		for (FieldError errorMessage : allErrors) {
			info.append(errorMessage.getDefaultMessage()).append("; ");
		}
		return fail(402, info.toString());
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public Object doHttpMessageConversionException(HttpMessageConversionException e) {
		log.info("数据异常,{}", e.getMessage());
		return fail(403, "数据异常");
	}

	/**
	 * 访问不存在的页面
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public Object doNoHandlerFoundException(Exception e) {
		log.info("页面不存在,{}", e.getMessage());
		return fail(404, "操作异常");
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Object doMaxUploadSizeExceededException(Exception e) {
		log.info("文件过大,{}", e.getMessage());
		return fail(403, "文件过大");
	}

}

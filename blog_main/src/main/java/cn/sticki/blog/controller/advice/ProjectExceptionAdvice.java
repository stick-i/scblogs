package cn.sticki.blog.controller.advice;

import cn.sticki.blog.exception.UserException;
import cn.sticki.blog.exception.userException.SqlLimitException;
import cn.sticki.blog.exception.userException.UserIllegalException;
import cn.sticki.blog.pojo.vo.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.util.ArrayList;

// 作为SpringMVC的异常处理器
@Slf4j
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

	@ExceptionHandler(HttpMessageConversionException.class)
	public RestTemplate doHttpMessageConversionException(HttpMessageConversionException e) {
		e.printStackTrace();
		return new RestTemplate(400, "数据异常", null, false);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public RestTemplate doHttpRequestMethodNotSupportedException(Exception e) {
		log.warn("请求方式异常,{}", e.getMessage());
		return new RestTemplate(400, "请求方式异常", null, false);
	}

	@ExceptionHandler({MissingRequestValueException.class, IllegalArgumentException.class, NullPointerException.class, TypeMismatchException.class, BindException.class})
	public RestTemplate doIllegalArgumentException(Exception e) {
		log.warn("参数异常,{}", e.getMessage());
		return new RestTemplate(400, "参数异常", null, false);
	}

	@ExceptionHandler(UserException.class)
	public RestTemplate doUserException(UserException e) {
		log.warn("用户操作异常,{}", e.getMessage());
		return new RestTemplate(400, e.getMessage(), null, false);
	}

	@ExceptionHandler(UserIllegalException.class)
	public RestTemplate doUserIllegalException(UserIllegalException e) {
		log.warn("用户非法操作,{}", e.getMessage());
		// todo 对非法操作的用户进行记录，一定数量后禁止访问
		return new RestTemplate(402, e.getMessage(), null, false);
	}

	// 访问不存在的页面
	@ExceptionHandler(NoHandlerFoundException.class)
	public RestTemplate doNoHandlerFoundException(Exception e) {
		log.warn("页面不存在,{}", e.getMessage());
		return new RestTemplate(404, "操作异常", null, false);
	}

	@ExceptionHandler(ConnectException.class)
	public RestTemplate doConnectException(ConnectException e) {
		log.warn("服务连接失败,{}", e.getMessage());
		return new RestTemplate(501, "服务器连接故障，请联系管理员", null, false);
	}

	@ExceptionHandler(SqlLimitException.class)
	public RestTemplate doSqlLimitException(SqlLimitException e) {
		log.info("请求分页数量超过上限,{}", e.getMessage());
		return new RestTemplate(200, e.getMessage(), new ArrayList<>(), false);
	}

}

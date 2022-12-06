package cn.sticki.common.web.advice;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Web程序默认异常处理器开关
 *
 * @author 阿杆
 * @deprecated 已开启自动注入，无需再使用该注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DefaultExceptionAdvice.class)
public @interface EnableDefaultExceptionAdvice {

}

package cn.sticki.common.web.advice;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Web程序默认响应体包装处理器开关
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/9/24 15:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DefaultResponseAdvice.class)
public @interface EnableDefaultResponseAdvice {

}

package cn.sticki.common.web.advice;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Web程序默认响应体包装处理器开关
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/9/24 15:25
 * @deprecated 已开启自动注入，无需再使用该注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DefaultResponseAdvice.class)
public @interface EnableDefaultResponseAdvice {

}

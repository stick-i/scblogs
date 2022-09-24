package cn.sticki.common.web.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拒绝默认响应体包装处理器的注解
 * <p>
 * 使用该注解的方法将不会被默认包装处理器进行包装
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/9/24 15:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDefaultResponseAdvice {

}

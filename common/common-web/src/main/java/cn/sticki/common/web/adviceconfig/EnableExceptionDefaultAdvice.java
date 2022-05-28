package cn.sticki.common.web.adviceconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExceptionDefaultAdvice.class)
public @interface EnableExceptionDefaultAdvice {

}

package cn.sticki.common.web.anno;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * Request 请求限制拦截
 *
 * @author 阿杆
 * @version 1.0
 * @date 2022/7/31 20:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

	/**
	 * 允许访问的次数，默认值120
	 */
	int count() default 120;

	/**
	 * 间隔的时间段，单位秒，默认值60
	 */
	int time() default 60;

	/**
	 * 访问达到限制后需要等待的世界，单位秒，默认值120
	 */
	int waits() default 120;

}

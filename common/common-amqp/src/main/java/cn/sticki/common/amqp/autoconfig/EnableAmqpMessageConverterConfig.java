package cn.sticki.common.amqp.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 阿杆
 * @version 1.0
 * @date 2022/6/25 18:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AmqpMessageConverterConfig.class)
public @interface EnableAmqpMessageConverterConfig {

}

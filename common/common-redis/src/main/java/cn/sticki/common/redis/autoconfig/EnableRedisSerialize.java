package cn.sticki.common.redis.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Redis默认序列化配置开关
 *
 * @author 阿杆
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisSerializeDefaultConfig.class)
public @interface EnableRedisSerialize {

}

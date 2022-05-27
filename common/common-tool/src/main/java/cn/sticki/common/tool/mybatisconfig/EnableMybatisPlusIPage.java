package cn.sticki.common.tool.mybatisconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisPlusWebConfig.class)
public @interface EnableMybatisPlusIPage {

}

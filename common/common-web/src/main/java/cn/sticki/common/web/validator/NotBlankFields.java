package cn.sticki.common.web.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段非空校验
 * <p>
 * 适用于List<String>类型的字段，要求List不为空，且List中的每个元素都不能为空
 *
 * @author 阿杆
 * @date 2024/1/16
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotBlankFieldsValidator.class})
public @interface NotBlankFields {

	String message() default "所有字段均不能为空";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}

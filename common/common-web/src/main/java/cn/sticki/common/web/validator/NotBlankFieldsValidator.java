package cn.sticki.common.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * @author 阿杆
 * @date 2024/1/16
 */
public class NotBlankFieldsValidator implements ConstraintValidator<NotBlankFields, List<String>> {

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
			return false;
		}
		for (String s : value) {
			if (s == null || s.isBlank()) {
				return false;
			}
		}
		return true;
	}

}

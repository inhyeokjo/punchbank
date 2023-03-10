package com.kkb.punchbank.global.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class LocalDateTimeDurationValidator implements ConstraintValidator<Duration, LocalDateTime> {

	private long beforeSecond;

	@Override
	public void initialize(Duration constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.beforeSecond = constraintAnnotation.beforeSecond();
	}

	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		return value.isBefore(LocalDateTime.now()) && value.isAfter(LocalDateTime.now().minusSeconds(beforeSecond));
	}
}

package com.kkb.punchbank.global.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateTimeDurationValidator.class)
public @interface Duration {
	String message();

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	long beforeSecond();

}

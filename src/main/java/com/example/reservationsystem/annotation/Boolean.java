package com.example.reservationsystem.annotation;

import com.example.reservationsystem.annotation.validator.BooleanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {BooleanValidator.class})
public @interface Boolean {
    String message() default "Value must be true or false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

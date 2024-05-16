package com.example.reservationsystem.annotation;

import com.example.reservationsystem.annotation.validator.FutureTimestampValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = FutureTimestampValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface FutureTimestamp {
    String message() default "Timestamp should be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
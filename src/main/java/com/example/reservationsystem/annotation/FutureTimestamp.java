package com.example.reservationsystem.annotation;

import com.example.reservationsystem.annotation.validator.FutureTimestampValidator;
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
@Constraint(validatedBy = FutureTimestampValidator.class)
public @interface FutureTimestamp {
    String message() default "Timestamp should be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
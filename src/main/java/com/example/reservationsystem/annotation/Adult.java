package com.example.reservationsystem.annotation;

import com.example.reservationsystem.annotation.validator.AdultValidator;
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
@Constraint(validatedBy = {AdultValidator.class})
public @interface Adult {
    String message() default "Age must be more than 18!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
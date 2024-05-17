package com.example.reservationsystem.annotation;

import com.example.reservationsystem.annotation.validator.RatingValidator;
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
@Constraint(validatedBy = {RatingValidator.class})
public @interface Rating {
    String message() default "The rating must be in the range from 1 to 5!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

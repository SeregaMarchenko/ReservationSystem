package com.example.reservationsystem.annotation.validator;

import com.example.reservationsystem.annotation.Boolean;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BooleanValidator implements ConstraintValidator<Boolean, String> {
    @Override
    public void initialize(Boolean constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("true") || value.equals("false");
    }
}

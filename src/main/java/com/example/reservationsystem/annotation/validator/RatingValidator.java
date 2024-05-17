package com.example.reservationsystem.annotation.validator;

import com.example.reservationsystem.annotation.Rating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, Integer> {
    @Override
    public void initialize(Rating constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (value >= 1 && value <= 5);
    }
}

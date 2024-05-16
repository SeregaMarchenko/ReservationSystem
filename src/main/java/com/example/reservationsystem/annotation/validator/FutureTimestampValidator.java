package com.example.reservationsystem.annotation.validator;

import com.example.reservationsystem.annotation.FutureTimestamp;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Timestamp;

public class FutureTimestampValidator implements ConstraintValidator<FutureTimestamp, Timestamp> {

    @Override
    public void initialize(FutureTimestamp constraintAnnotation) {
    }

    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext context) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return timestamp != null && timestamp.after(now);
    }
}
package com.example.learn_java_spring.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        // Không chứa khoảng trắng và phải bắt đầu bằng chữ cái
        return value.matches("^[A-Za-z][A-Za-z0-9_]{2,19}$");
    }
}

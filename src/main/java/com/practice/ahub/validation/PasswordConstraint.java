package com.practice.ahub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password should contain at least one uppercase letter, one digit, one lowercase letter and minimum length 8";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
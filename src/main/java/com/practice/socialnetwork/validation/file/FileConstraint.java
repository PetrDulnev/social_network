package com.practice.socialnetwork.validation.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {FileValidator.class})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileConstraint {
    String message() default "unsupported file format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

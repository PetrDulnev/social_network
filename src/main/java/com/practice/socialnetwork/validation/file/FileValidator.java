package com.practice.socialnetwork.validation.file;

import com.practice.socialnetwork.model.file.AccessFileExtension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<FileConstraint, MultipartFile> {
    @Override
    public void initialize(FileConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            try {
                AccessFileExtension.valueOf(file.getContentType().split("/")[1].toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }
}


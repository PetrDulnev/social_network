package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.model.AccessFileExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileModelService {

    public void isValidFile(MultipartFile file) {
        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            try {
                AccessFileExtension.valueOf(file.getContentType().split("/")[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CustomException("unsupported file format");
            }
        }
    }
}

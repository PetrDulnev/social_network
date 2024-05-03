package com.practice.ahub.service;

import com.practice.ahub.model.AccessFileExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileModelService {

    public boolean isValidFile(MultipartFile file) {
        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
            try {
                AccessFileExtension.valueOf(file.getContentType().split("/")[1].toUpperCase());
                return true;
            }catch (IllegalArgumentException e){
                return false;
            }
        }
        return false;
    }
}

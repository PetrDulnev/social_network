package com.practice.socialnetwork.service.minio;

import com.practice.socialnetwork.model.file.FileModel;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    @SneakyThrows
    String save(MultipartFile file);

    @SneakyThrows
    String createBucketIfNotExistsOrNot(String bucketName);

    @SneakyThrows
    String getFileUrl(FileModel file);

    @PostConstruct
    void init();
}

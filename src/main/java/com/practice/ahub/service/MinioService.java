package com.practice.ahub.service;

import com.practice.ahub.model.FileModel;
import com.practice.ahub.properties.MinioProperties;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioProperties minioProperties;
    private MinioClient minioClient;

    @SneakyThrows
    public String save(MultipartFile file) {
        String fileName = UUID.randomUUID().toString().concat(file.getOriginalFilename());
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(fileName)
                        .bucket(checkBucket(file.getContentType().split("/")[0]))
                        .build()
        );
        return fileName;
    }

    @SneakyThrows
    private String checkBucket(String s) {
        if (!minioClient.bucketExists(BucketExistsArgs
                .builder().bucket(s).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(s)
                    .build());
        }
        return s;
    }

    @SneakyThrows
    public String getFileUrl(FileModel file) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(file.getContentType())
                        .object(file.getFileName())
                        .expiry(1, TimeUnit.MINUTES)
                        .build()
        );
    }


    @PostConstruct
    public void init() {
        minioClient =
                MinioClient.builder()
                        .endpoint(minioProperties.getUrl())
                        .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                        .build();
    }
}

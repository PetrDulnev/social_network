package com.practice.socialnetwork.service.minio;

import com.practice.socialnetwork.config.MinioPropertiesConfiguration;
import com.practice.socialnetwork.model.file.FileModel;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioPropertiesConfiguration minioPropertiesConfiguration;
    private MinioClient minioClient;

    @Override
    @SneakyThrows
    public String save(MultipartFile file) {

        String fileName = UUID.randomUUID().toString().concat(file.getOriginalFilename());

        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(fileName)
                        .bucket(createBucketIfNotExistsOrNot(file.getContentType().split("/")[0]))
                        .build()
        );

        return fileName;
    }

    @Override
    @SneakyThrows
    public String createBucketIfNotExistsOrNot(String bucketName) {
        if (!minioClient.bucketExists(BucketExistsArgs
                .builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
        return bucketName;
    }

    @Override
    @SneakyThrows
    public String getFileUrl(FileModel file) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(file.getContentType().split("/")[0])
                        .object(file.getFileName())
                        .expiry(1, TimeUnit.MINUTES)
                        .method(Method.GET)
                        .build()
        );
    }


    @Override
    @PostConstruct
    public void init() {
        minioClient =
                MinioClient.builder()
                        .endpoint(minioPropertiesConfiguration.getUrl())
                        .credentials(minioPropertiesConfiguration.getAccessKey(), minioPropertiesConfiguration.getSecretKey())
                        .build();
    }
}

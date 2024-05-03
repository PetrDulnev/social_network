package com.practice.socialnetwork.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioPropertiesConfiguration {
    private String url;
    private String accessKey;
    private String secretKey;
}

package com.practice.ahub.modelDto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.practice.ahub.model.Comment}
 */
@Value
public class CommentDto implements Serializable {
    Long id;
    String comment;
    UserProfileDto user;
    LocalDateTime createdAt;
}
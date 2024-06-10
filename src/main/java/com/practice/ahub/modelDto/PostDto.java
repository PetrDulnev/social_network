package com.practice.ahub.modelDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.practice.ahub.model.Post}
 */
@Value
public class PostDto implements Serializable {
    Long id;
    @NotEmpty
    String body;
    UserProfileDto user;
    LocalDateTime createdAt;
    List<CommentDto> comments;
}
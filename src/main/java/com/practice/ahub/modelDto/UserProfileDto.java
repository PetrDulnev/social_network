package com.practice.ahub.modelDto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.ahub.model.UserProfile}
 */
@Value
public class UserProfileDto implements Serializable {
    Long id;
    String link;
    UserDto user;
}
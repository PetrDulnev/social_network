package com.practice.ahub.modelDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.practice.ahub.model.User}
 */
@Value
public class UserDto implements Serializable {
    @NotBlank
    String name;
    @NotBlank
    String surname;
}
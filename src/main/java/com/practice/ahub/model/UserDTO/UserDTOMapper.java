package com.practice.ahub.model.UserDTO;

import com.practice.ahub.model.User;

public class UserDTOMapper {
    public static UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getName())
                .build();
    }
}

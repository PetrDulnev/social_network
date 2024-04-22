package com.practice.ahub.model.UserDTO;

import lombok.*;


@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private String email;
    private String surname;
}

package com.practice.ahub.service;

import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    UserDTO createUser(User user);

    User loadUserByUsername(String username);

    JwtResponse login(JwtRequest request);

    UserDTO getUserById(Long id);
}

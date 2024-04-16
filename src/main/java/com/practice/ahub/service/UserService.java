package com.practice.ahub.service;

import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    User createUser(User user);

    User loadUserByUsername(String username);

    ResponseEntity<JwtResponse> login(JwtRequest request);
}

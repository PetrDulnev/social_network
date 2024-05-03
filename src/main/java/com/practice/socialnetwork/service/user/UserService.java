package com.practice.socialnetwork.service.user;

import com.practice.socialnetwork.jwt.JwtRequest;
import com.practice.socialnetwork.jwt.JwtResponse;
import com.practice.socialnetwork.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    User createUser(User user);

    User loadUserByUsername(String username);

    ResponseEntity<JwtResponse> login(JwtRequest request);
}

package com.practice.socialnetwork.controller;

import com.practice.socialnetwork.jwt.JwtRequest;
import com.practice.socialnetwork.jwt.JwtResponse;
import com.practice.socialnetwork.model.user.User;
import com.practice.socialnetwork.service.user.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    @PermitAll
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        return userService.login(request);
    }
}
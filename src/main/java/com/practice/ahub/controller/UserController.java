package com.practice.ahub.controller;

import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.User;
import com.practice.ahub.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ahub/users")
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
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/log")
    @Secured("USER")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/log/admin")
    @Secured("ADMIN")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

}
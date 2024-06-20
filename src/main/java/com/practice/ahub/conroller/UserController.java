package com.practice.ahub.conroller;

import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.User;
import com.practice.ahub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "UserController", hidden = true)
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    @PermitAll
    @ApiOperation(value = "Create new user", notes = "Create new User and automatically create userprofile")
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
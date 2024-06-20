package com.practice.ahub.conroller;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import com.practice.ahub.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping(("/{id}"))
    public User getUser(@PathVariable Long id) {

        return userService.getById(id).orElseThrow(
                () -> new CustomException(
                        String.format("user with id = %s not found", id)
                )
        );
    }

    @PostMapping("/create/million")
    public void createMillionUsers() {
        for (int i = 0; i < 1000; i++) {
            userService.createUser(
                    new User(
                            null,
                            "asd" + i + "@mail.ru",
                            "asdassddas",
                            "asdsad",
                            "asdsda",
                            Role.USER
                    )
            );
        }
    }
}
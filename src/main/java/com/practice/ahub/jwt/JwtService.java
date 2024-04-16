package com.practice.ahub.jwt;

import com.practice.ahub.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;

    public ResponseEntity<JwtResponse> generateToken(User subject) {
        String token = jwtProvider.generateToken(subject);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return ResponseEntity.ok(jwtResponse);
    }
}

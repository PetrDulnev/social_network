package com.practice.socialnetwork.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private final String start = "Bearer ";
    private String token;

    @Override
    public String toString() {
        return start + token;
    }
}

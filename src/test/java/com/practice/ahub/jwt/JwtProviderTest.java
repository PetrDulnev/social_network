package com.practice.ahub.jwt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JwtProviderTest {

    static JwtProvider jwtProvider;

    String checkedToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTMzNDg5ODYsImV4cCI6MTcxMzM0OTAxNiwic3ViIjoidXNlcjEzMzdAbWFpbC5ydSIsInJvbGUiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.8R0YVJlsroKzkyJFHJ8mZy-nuVKS4vQpNFGEc0KOLh";

    @BeforeAll
    static void setUp() {
        jwtProvider = new JwtProvider();
    }

    @Test
    void getUserNameFromToken() {
        String userName = jwtProvider.getUserNameFromToken(checkedToken);
        assertEquals(userName, "user1337@mail.ru");
    }

    @Test
    void getRolesFromToken() {
        String roles = jwtProvider.getRolesFromToken(checkedToken);
        assertEquals(roles, "USER");
    }

    @Test
    void isValidToken() {
        assertFalse(jwtProvider.isValidToken(checkedToken));
    }
}
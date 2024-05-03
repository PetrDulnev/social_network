package com.practice.socialnetwork.jwt;


import com.practice.socialnetwork.exception.CustomException;
import com.practice.socialnetwork.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final String secret =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJ";
    SecretKeySpec key = new
            SecretKeySpec(secret.getBytes(), "HmacSHA256");

    public String generateToken(User user) {
        LocalDateTime dateTime = LocalDateTime.now();
        Date issue = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date exp = Date.from(dateTime.plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .signWith(key)
                .issuedAt(issue)
                .expiration(exp)
                .subject(user.getUsername())
                .claim("role", user.getAuthorities())
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getRolesFromToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (String) ((HashMap<?, ?>) claimsFromToken.get("role", ArrayList.class).get(0)).get("authority");
    }

    public Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        return (Claims) parser.parse(token).getPayload();
    }

    public boolean isValidToken(String token) {
        Date deadTime = getClaimsFromToken(token).getExpiration();
        if ((token != null) && !deadTime.before(new Date(System.currentTimeMillis()))) {
            return true;
        } else {
            throw new CustomException("Expired or invalid JWT token");
        }
    }
}

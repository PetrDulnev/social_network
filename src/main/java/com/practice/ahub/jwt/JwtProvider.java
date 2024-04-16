package com.practice.ahub.jwt;


import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        Date exp = Date.from(dateTime.plusMinutes(1000).atZone(ZoneId.systemDefault()).toInstant());
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
        return (String) ((HashMap) claimsFromToken.get("role", ArrayList.class).get(0)).get("authority");
    }

    public Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        return (Claims) parser.parse(token).getPayload();
    }

    public boolean isValidToken (String token) {
        Date deadTime = getClaimsFromToken(token).getExpiration();
        return (token != null) || !deadTime.before(new Date(System.currentTimeMillis()));
    }
}

package com.practice.ahub.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.ahub.exception.ExceptionResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                log.info("JWT Token: {}", token);
                if (jwtProvider.isValidToken(token)) {
                    String authority = jwtProvider.getRolesFromToken(token);
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
                    log.info("JWT Authorities: {}", authority);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            jwtProvider.getUserNameFromToken(token),
                            null,
                            List.of(grantedAuthority)
                    );
                    log.info("JWT Authentication: {}", authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException e) {
            setErrorResponse((HttpServletResponse) servletResponse, e);
            throw new JwtException(e.getMessage());
        }

    }


    private void setErrorResponse(HttpServletResponse response, JwtException jwtException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                true, jwtException.getMessage()
        );
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}

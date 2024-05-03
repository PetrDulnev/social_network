package com.practice.socialnetwork.model.user;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    Role(String authority) {
        this.authority = authority;
    }

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}

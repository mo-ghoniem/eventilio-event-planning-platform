package com.moghoneim.user_service.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    public String getCurrentUserId() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof Jwt jwt) {
                return jwt.getSubject(); // return user ID (sub)
            } else {
                throw new IllegalStateException("Invalid authentication principal, JWT expected.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract user ID from token: " + e.getMessage(), e);
        }
    }
}

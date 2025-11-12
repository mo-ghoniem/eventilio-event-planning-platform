package com.moghoneim.user_service.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProvider {

    public String getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }

        System.out.println("Unexpected authentication type: " + (authentication != null ? authentication.getClass() : "null"));
        return null;
    }
}



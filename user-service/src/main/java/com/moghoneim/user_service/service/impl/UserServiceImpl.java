package com.moghoneim.user_service.service.impl;


import com.moghoneim.user_service.dto.UserDto;
import com.moghoneim.user_service.dto.UserRecord;
import com.moghoneim.user_service.keycloak.KeycloakUserManager;
import com.moghoneim.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakUserManager keycloakUserManager;

    @Override
    public UserRecord getUser(String userName) {
        return keycloakUserManager.getUserByUsername(userName);
    }

    @Override
    public void updateUser(String userId, UserDto userDTO) {
        keycloakUserManager.updateUser(userId, userDTO);
    }

    @Override
    public void deleteUser(String userId) {
        keycloakUserManager.deleteUser(userId);
    }

    @Override
    public void sendVerificationLink(String userId) {
        keycloakUserManager.sendVerificationLink(userId);
    }

    @Override
    public void sendResetPassword(String userId) {
        keycloakUserManager.sendResetPasswordLink(userId);
    }

    @Override
    public Map<String, String> getCurrentUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Jwt jwt) {
            String userId = jwt.getSubject(); // Keycloak user ID
            String username = jwt.getClaimAsString("preferred_username");

            return Map.of(
                    "userId", userId,
                    "username", username
            );
        }

        throw new RuntimeException("Unauthorized: No valid JWT found");
    }

}

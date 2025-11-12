package com.moghoneim.user_service.service.impl;

import com.moghoneim.user_service.dto.LoginDto;
import com.moghoneim.user_service.dto.UserDto;
import com.moghoneim.user_service.keycloak.KeycloakAuthClient;
import com.moghoneim.user_service.keycloak.KeycloakUserManager;
import com.moghoneim.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakUserManager keycloakUserManager;
    private final KeycloakAuthClient keycloakAuthClient;

    @Override
    public String registerUser(UserDto userDto) {
        return keycloakUserManager.registerUserWithRole(userDto, "user");
    }

    @Override
    public String authenticateUser(LoginDto loginDto) {
        return keycloakAuthClient.authenticate(loginDto);
    }
}

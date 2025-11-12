package com.moghoneim.user_service.service;

import com.moghoneim.user_service.dto.UserDto;
import com.moghoneim.user_service.dto.UserRecord;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserRecord getUser(String userName);
    Map<String, String> getCurrentUserInfo();
    void updateUser(String userId, UserDto userDTO);
    void deleteUser(String userId);
    void sendVerificationLink(String userId);
    void sendResetPassword(String userId);
}

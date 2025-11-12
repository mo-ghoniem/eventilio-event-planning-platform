package com.moghoneim.user_service.service;

import com.moghoneim.user_service.dto.LoginDto;
import com.moghoneim.user_service.dto.UserDto;

public interface AuthService {
    public String  registerUser(UserDto userDto);
    public String authenticateUser(LoginDto loginDto);
}

package com.moghoneim.user_service.exception;

public class RegisterUserException extends RuntimeException {

    public RegisterUserException(String message) {
        super(message);
    }
    public RegisterUserException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.moghoneim.user_service.exception;

public class UserDeletionException extends RuntimeException {

    public UserDeletionException(String message) {
        super(message);
    }

    public UserDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.moghoneim.user_service.dto;

public record UserRecord(
        String id,
        String firstName,
        String lastName,
        String email,
        boolean emailVerified,
        long createdTimestamp
) {}

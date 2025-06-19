package com.modsen.user_service.dto;

import com.modsen.user_service.util.Role;

import java.time.Instant;
import java.util.UUID;

public record AdminUserResponseDto(
        UUID id,
        String username,
        String email,
        Role role,
        boolean isActive,
        Instant createdAt
) {}
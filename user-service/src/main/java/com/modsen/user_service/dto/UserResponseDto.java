package com.modsen.user_service.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String username,
        Instant createdAt
) {}

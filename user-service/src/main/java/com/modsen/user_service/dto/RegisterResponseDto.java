package com.modsen.user_service.dto;

import java.util.UUID;

public record RegisterResponseDto(String message, UUID userId) { }
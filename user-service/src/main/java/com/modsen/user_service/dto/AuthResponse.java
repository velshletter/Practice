package com.modsen.user_service.dto;

import java.util.UUID;

public record AuthResponse(String message, UUID userId) { }
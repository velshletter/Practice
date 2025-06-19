package com.modsen.user_service.dto;

import java.util.UUID;

public record AuthResponseDto(String accessToken, UUID refreshToken) {
}

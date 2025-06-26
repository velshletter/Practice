package com.modsen.poll_service.dto;

import java.time.Instant;
import java.util.UUID;

public record VoteResponseDto(
        UUID id,
        UUID pollId,
        UUID optionId,
        UUID userId,
        Instant createdAt,
        String ipAddress
) {}
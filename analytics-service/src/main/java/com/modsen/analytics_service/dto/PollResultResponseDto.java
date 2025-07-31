package com.modsen.analytics_service.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PollResultResponseDto(
        UUID pollId,
        long totalVotes,
        List<OptionResultDto> options,
        Instant calculatedAt
) {}
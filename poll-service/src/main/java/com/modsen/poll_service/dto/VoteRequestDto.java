package com.modsen.poll_service.dto;

import java.util.UUID;

public record VoteRequestDto(
        UUID pollId,
        UUID optionId,
        String ipAddress
) {}
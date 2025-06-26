package com.modsen.poll_service.dto;

import java.util.UUID;

public record OptionResponseDto(
        UUID id,
        UUID pollId,
        String text,
        Integer order
) {}
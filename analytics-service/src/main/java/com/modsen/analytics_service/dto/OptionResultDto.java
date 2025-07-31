package com.modsen.analytics_service.dto;

import java.util.UUID;

public record OptionResultDto(
        UUID optionId,
        String text,
        long votes,
        double percentage
) {}
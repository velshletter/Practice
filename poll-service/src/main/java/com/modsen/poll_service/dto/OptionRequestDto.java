package com.modsen.poll_service.dto;

public record OptionRequestDto(
        String text,
        Integer order
) {}
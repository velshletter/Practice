package com.modsen.poll_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollRequestDto {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Boolean isAnonymous;

    @NotNull
    private Boolean isMultipleChoice;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private List<String> tags;

    @NotNull
    private UUID createdBy;
}
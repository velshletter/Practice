package com.modsen.poll_service.dto;

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
public class PollResponseDto {

    private UUID id;
    private String title;
    private String description;
    private Boolean isAnonymous;
    private Boolean isMultipleChoice;
    private Instant startDate;
    private Instant endDate;
    private UUID createdBy;
    private Instant createdAt;
    private List<String> tags;
}
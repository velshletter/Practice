package com.modsen.base_domains.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollResultEvent {
    private UUID pollId;
    private String title;
    private boolean isAnonymous;
    private Instant startDate;
    private Instant endDate;
    private Long totalVotes;
    private Instant calculatedAt;
    private List<OptionResultEvent> options;
}
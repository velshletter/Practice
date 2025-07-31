package com.modsen.analytics_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;
@Entity
@Table(name = "poll_results")
@Getter
@Setter
@NoArgsConstructor
public class PollResult {

    @Id
    @Column(name = "poll_id", nullable = false, updatable = false)
    private UUID pollId;

    @Column(name = "total_votes", nullable = false)
    private Long totalVotes;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

    @OneToMany(mappedBy = "pollResult", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OptionResult> options = new ArrayList<>();

    public void addOptionResult(OptionResult optionResult) {
        optionResult.setPollResult(this);
        this.options.add(optionResult);
    }

    public void addAllOptionResults(List<OptionResult> optionResults) {
        optionResults.forEach(this::addOptionResult);
    }

}

package com.modsen.analytics_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "poll_result_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "option_id", nullable = false, updatable = false)
    private UUID optionId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Long votes;

    @Column(nullable = false)
    private Double percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_result_id", nullable = false)
    private PollResult pollResult;
}

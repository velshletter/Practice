package com.modsen.base_domains.events;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionResultEvent {

    private UUID optionId;
    private String text;
    private long votes;
    private double percentage;
}
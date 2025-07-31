package com.modsen.analytics_service.kafka;

import com.modsen.base_domains.events.PollResultEvent;
import com.modsen.analytics_service.dto.mapper.PollResultMapper;
import com.modsen.analytics_service.service.PollResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollResultConsumer {

    private final PollResultService pollResultService;
    private final PollResultMapper mapper;

    @KafkaListener(topics = "poll-results", groupId = "poll-group")
    public void consumePollResult(PollResultEvent event) {
        log.debug("Received event: {}", event);
        log.debug("Options: {}", event.getOptions());
        pollResultService.save(mapper.toEntityWithOptions(event));
    }
}
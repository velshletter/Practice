package com.modsen.poll_service.kafka;

import com.modsen.base_domains.events.PollResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollResultProducer {

    @Value("${kafka.topics.poll-results}")
    private String topic;

    private final KafkaTemplate<String, PollResultEvent> kafkaTemplate;

    public void sendPollResult(PollResultEvent event) {
        log.debug(event.toString());
        kafkaTemplate.send(topic, event.getPollId().toString(), event);
    }
}
package com.modsen.poll_service.scheduler;

import com.modsen.poll_service.entity.Poll;

import com.modsen.poll_service.repository.PollRepository;
import com.modsen.poll_service.service.PollClosingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class PollScheduler {

    private final PollRepository pollRepository;
    private final PollClosingService pollClosingService;

    @Scheduled(fixedRate = 60000)
    public void checkAndCompleteExpiredPolls() {
        log.info("===> Running scheduled task: checkAndCompleteExpiredPolls");
        List<Poll> expiredPolls = pollRepository.findByEndDateBeforeWithOptions(Instant.now());

        for (Poll poll : expiredPolls) {
            pollClosingService.closePoll(poll);
        }
    }


}
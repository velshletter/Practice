package com.modsen.poll_service.service.impl;

import com.modsen.base_domains.events.OptionResultEvent;
import com.modsen.base_domains.events.PollResultEvent;
import com.modsen.poll_service.entity.Option;
import com.modsen.poll_service.entity.Poll;
import com.modsen.poll_service.exception.PollNotFoundException;
import com.modsen.poll_service.kafka.PollResultProducer;
import com.modsen.poll_service.projection.VoteCountProjection;
import com.modsen.poll_service.repository.PollRepository;
import com.modsen.poll_service.repository.VoteRepository;
import com.modsen.poll_service.service.PollClosingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollClosingServiceImpl implements PollClosingService {

    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;
    private final PollResultProducer pollResultProducer;

    @Transactional
    public void closePoll(Poll poll) {
        Map<Option, Long> voteCounts = voteRepository.countVotesGroupedByOption(poll.getId()).stream()
                .collect(Collectors.toMap(VoteCountProjection::getOption, VoteCountProjection::getVoteCount));
        log.info("Received event: {}", voteCounts);

        long totalVotes = voteCounts.values().stream().mapToLong(Long::longValue).sum();

        List<OptionResultEvent> optionResults = poll.getOptions().stream()
                .map(option -> {
                    long votes = voteCounts.getOrDefault(option, 0L);
                    double percentage = totalVotes == 0 ? 0.0 : (votes * 100.0) / totalVotes;
                    return OptionResultEvent.builder()
                            .optionId(option.getId())
                            .text(option.getText())
                            .votes(votes)
                            .percentage(percentage)
                            .build();
                }).toList();

        PollResultEvent event = PollResultEvent.builder()
                .pollId(poll.getId())
                .title(poll.getTitle())
                .isAnonymous(poll.isAnonymous())
                .startDate(poll.getStartDate())
                .endDate(poll.getEndDate())
                .totalVotes(totalVotes)
                .calculatedAt(Instant.now())
                .options(optionResults)
                .build();

        pollResultProducer.sendPollResult(event);
        Poll managedPoll = pollRepository.findById(poll.getId())
                .orElseThrow(() -> new PollNotFoundException("Poll not found"));
        log.info(String.valueOf(managedPoll));
        pollRepository.delete(managedPoll);


    }
}

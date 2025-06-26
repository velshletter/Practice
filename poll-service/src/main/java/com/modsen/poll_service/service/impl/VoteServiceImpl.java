package com.modsen.poll_service.service.impl;

import com.modsen.poll_service.entity.Option;
import com.modsen.poll_service.entity.Poll;
import com.modsen.poll_service.entity.Vote;
import com.modsen.poll_service.repository.OptionRepository;
import com.modsen.poll_service.repository.PollRepository;
import com.modsen.poll_service.repository.VoteRepository;
import com.modsen.poll_service.service.VoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;

    @Override
    public void castVote(UUID pollId, UUID optionId, UUID userId, String ipAddress) {
        if (voteRepository.existsByPollIdAndUserId(pollId, userId)) {
            throw new IllegalStateException("User has already voted in this poll");
        }

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new EntityNotFoundException("Poll not found"));

        if (Instant.now().isBefore(poll.getStartDate()) || Instant.now().isAfter(poll.getEndDate())) {
            throw new IllegalStateException("Poll is not active");
        }

        Option option = optionRepository.findById(optionId)
                .filter(o -> o.getPoll().getId().equals(pollId))
                .orElseThrow(() -> new IllegalArgumentException("Option does not belong to the specified poll"));

        Vote vote = Vote.builder()
                .poll(poll)
                .option(option)
                .userId(userId)
                .ipAddress(ipAddress)
                .build();

        voteRepository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserVoted(UUID pollId, UUID userId) {
        return voteRepository.existsByPollIdAndUserId(pollId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vote> getVotesForPoll(UUID pollId) {
        return voteRepository.findAllByPollId(pollId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getVoteCountForOption(UUID pollId, UUID optionId) {
        return voteRepository.countByPollIdAndOptionId(pollId, optionId);
    }
}



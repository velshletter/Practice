package com.modsen.poll_service.service;

import com.modsen.poll_service.entity.Vote;

import java.util.List;
import java.util.UUID;

public interface VoteService {

    void castVote(UUID pollId, UUID optionId, UUID userId, String ipAddress);

    boolean hasUserVoted(UUID pollId, UUID userId);

    List<Vote> getVotesForPoll(UUID pollId);

    long getVoteCountForOption(UUID pollId, UUID optionId);
}

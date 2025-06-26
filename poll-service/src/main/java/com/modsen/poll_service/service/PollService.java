package com.modsen.poll_service.service;

import com.modsen.poll_service.dto.PollRequestDto;
import com.modsen.poll_service.dto.PollResponseDto;

import java.util.List;
import java.util.UUID;


import java.time.Instant;

public interface PollService {

    List<PollResponseDto> getPolls();

    PollResponseDto getPollById(UUID pollId);

    PollResponseDto createPoll(PollRequestDto pollRequestDto, UUID createdBy);

    PollResponseDto updatePoll(UUID pollId, PollRequestDto updatedPoll);

    void deletePoll(UUID pollId);

    void setPollDates(UUID pollId, Instant startDate, Instant endDate);

    void setPollAnonymity(UUID pollId, boolean isAnonymous);

    boolean hasUserVoted(UUID pollId, UUID userId);

    void finishPoll(UUID pollId);
}

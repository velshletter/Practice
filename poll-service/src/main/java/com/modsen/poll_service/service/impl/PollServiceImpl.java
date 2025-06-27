package com.modsen.poll_service.service.impl;

import com.modsen.poll_service.dto.PollRequestDto;
import com.modsen.poll_service.dto.PollResponseDto;
import com.modsen.poll_service.dto.mapper.PollMapper;
import com.modsen.poll_service.entity.Poll;
import com.modsen.poll_service.exception.PollNotFoundException;
import com.modsen.poll_service.repository.PollRepository;
import com.modsen.poll_service.repository.VoteRepository;
import com.modsen.poll_service.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final PollMapper pollMapper;

    @Override
    public List<PollResponseDto> getPolls() {
        return pollRepository.findAll()
                .stream()
                .map(pollMapper::toDto)
                .toList();
    }

    @Override
    public PollResponseDto getPollById(UUID pollId) {
        return pollMapper.toDto(findPollOrThrow(pollId));
    }

    @Override
    @Transactional
    public PollResponseDto createPoll(PollRequestDto request, UUID createdBy) {

        Poll poll = pollMapper.toEntity(request);
        poll.setCreatedBy(createdBy);
        return pollMapper.toDto(pollRepository.save(poll));
    }

    @Override
    @Transactional
    public PollResponseDto updatePoll(UUID pollId, PollRequestDto updatedPoll) {
        Poll existingPoll = findPollOrThrow(pollId);

        existingPoll.setTitle(updatedPoll.getTitle());
        existingPoll.setDescription(updatedPoll.getDescription());
        existingPoll.setMultipleChoice(updatedPoll.getIsMultipleChoice());

        return pollMapper.toDto(pollRepository.save(existingPoll));
    }


    @Override
    @Transactional
    public void deletePoll(UUID pollId) {
        pollRepository.delete(findPollOrThrow(pollId));
    }

    @Override
    @Transactional
    public void setPollDates(UUID pollId, Instant startDate, Instant endDate) {
        Poll poll = findPollOrThrow(pollId);
        poll.setStartDate(startDate);
        poll.setEndDate(endDate);
        pollRepository.save(poll);
    }

    @Override
    @Transactional
    public void setPollAnonymity(UUID pollId, boolean isAnonymous) {
        Poll poll = findPollOrThrow(pollId);
        poll.setAnonymous(isAnonymous);
        pollRepository.save(poll);
    }

    @Override
    @Transactional
    public void finishPoll(UUID pollId) {
        Poll poll = findPollOrThrow(pollId);
        pollRepository.save(poll);
    }

    @Override
    public boolean hasUserVoted(UUID pollId, UUID userId) {
        return voteRepository.existsByPollIdAndUserId(pollId, userId);
    }
    
    private Poll findPollOrThrow(UUID pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException("Poll not found with id: " + pollId));
    }

}

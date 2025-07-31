package com.modsen.analytics_service.service.impl;

import com.modsen.analytics_service.dto.PollResultResponseDto;
import com.modsen.analytics_service.dto.mapper.PollResultMapper;
import com.modsen.analytics_service.entity.PollResult;
import com.modsen.analytics_service.repository.PollResultRepository;
import com.modsen.analytics_service.service.PollResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollResultServiceImpl implements PollResultService {

    private final PollResultMapper pollResultMapper;
    private final PollResultRepository pollResultRepository;

    public List<PollResultResponseDto> getAllPollResults() {
        return pollResultRepository.findAll()
                .stream()
                .map(pollResultMapper::toDto)
                .toList();
    }

    @Transactional
    public void save(PollResult pollResult) {
        PollResult savedPollResult = pollResultRepository.save(pollResult);
        pollResultMapper.toDto(savedPollResult);
    }

}


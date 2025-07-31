package com.modsen.analytics_service.service;

import com.modsen.analytics_service.dto.PollResultResponseDto;
import com.modsen.analytics_service.entity.PollResult;

import java.util.List;

public interface PollResultService {

    void save(PollResult pollResult);

    List<PollResultResponseDto> getAllPollResults();
}
package com.modsen.poll_service.service;

import com.modsen.poll_service.dto.OptionRequestDto;
import com.modsen.poll_service.dto.OptionResponseDto;

import java.util.List;
import java.util.UUID;

public interface OptionService {
    OptionResponseDto addOption(UUID pollId, OptionRequestDto dto);

    void deleteOption(UUID optionId);

    List<OptionResponseDto> getOptionsByPoll(UUID pollId);
}

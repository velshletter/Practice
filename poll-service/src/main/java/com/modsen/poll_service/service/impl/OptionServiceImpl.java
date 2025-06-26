package com.modsen.poll_service.service.impl;

import com.modsen.poll_service.dto.OptionRequestDto;
import com.modsen.poll_service.dto.OptionResponseDto;
import com.modsen.poll_service.dto.mapper.OptionMapper;
import com.modsen.poll_service.entity.Option;
import com.modsen.poll_service.entity.Poll;
import com.modsen.poll_service.repository.OptionRepository;
import com.modsen.poll_service.repository.PollRepository;
import com.modsen.poll_service.service.OptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final PollRepository pollRepository;
    private final OptionMapper optionMapper;

    @Override
    public OptionResponseDto addOption(UUID pollId, OptionRequestDto dto) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new EntityNotFoundException("Poll not found"));

        Option option = optionMapper.toEntity(dto);
        option.setPoll(poll);

        return optionMapper.toDto(optionRepository.save(option));
    }

    @Override
    public void deleteOption(UUID optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("Option not found"));
        optionRepository.delete(option);
    }

    @Override
    public List<OptionResponseDto> getOptionsByPoll(UUID pollId) {
        return optionMapper.toDtoList(optionRepository.findByPollId(pollId));
    }
}

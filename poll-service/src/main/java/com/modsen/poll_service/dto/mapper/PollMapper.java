package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.PollRequestDto;
import com.modsen.poll_service.dto.PollResponseDto;
import com.modsen.poll_service.entity.Poll;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PollMapper {

    Poll toEntity(PollRequestDto dto);

    PollResponseDto toDto(Poll entity);
}

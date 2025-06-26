package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.VoteResponseDto;
import com.modsen.poll_service.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    VoteResponseDto toDto(Vote vote);

    Vote toEntity(VoteResponseDto dto);
}
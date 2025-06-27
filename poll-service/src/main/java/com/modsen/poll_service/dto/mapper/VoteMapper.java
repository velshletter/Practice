package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.VoteResponseDto;
import com.modsen.poll_service.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VoteMapper {

    @Mapping(target = "pollId", source = "poll.id")
    @Mapping(target = "optionId", source = "option.id")
    VoteResponseDto toDto(Vote vote);

    Vote toEntity(VoteResponseDto dto);
}
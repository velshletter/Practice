package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.OptionRequestDto;
import com.modsen.poll_service.dto.OptionResponseDto;
import com.modsen.poll_service.entity.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    @Mapping(source = "poll.id", target = "pollId")
    OptionResponseDto toDto(Option option);

    List<OptionResponseDto> toDtoList(List<Option> options);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "poll", ignore = true)
    @Mapping(target = "votes", ignore = true)
    Option toEntity(OptionRequestDto dto);
}



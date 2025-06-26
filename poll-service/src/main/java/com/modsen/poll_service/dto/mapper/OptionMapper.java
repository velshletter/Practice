package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.OptionRequestDto;
import com.modsen.poll_service.dto.OptionResponseDto;
import com.modsen.poll_service.entity.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OptionMapper {

    @Mapping(source = "poll.id", target = "pollId")
    OptionResponseDto toDto(Option option);

    List<OptionResponseDto> toDtoList(List<Option> options);

    Option toEntity(OptionRequestDto dto);
}



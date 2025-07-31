package com.modsen.poll_service.dto.mapper;

import com.modsen.poll_service.dto.PollRequestDto;
import com.modsen.poll_service.dto.PollResponseDto;
import com.modsen.poll_service.entity.Poll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PollMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "options", ignore = true)
    Poll toEntity(PollRequestDto dto);

    @Mapping(target = "isAnonymous", source = "anonymous")
    @Mapping(target = "isMultipleChoice", source = "multipleChoice")
    PollResponseDto toDto(Poll entity);
}

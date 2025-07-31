package com.modsen.analytics_service.dto.mapper;

import com.modsen.analytics_service.entity.OptionResult;
import com.modsen.base_domains.events.PollResultEvent;
import com.modsen.analytics_service.dto.PollResultResponseDto;
import com.modsen.analytics_service.entity.PollResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface PollResultMapper {

    @Mapping(target = "options", ignore = true)
    PollResult toEntity(PollResultEvent event);

    PollResultResponseDto toDto(PollResult entity);

    default PollResult toEntityWithOptions(PollResultEvent event) {
        PollResult result = toEntity(event);

        List<OptionResult> optionResults = Optional.ofNullable(event.getOptions())
                .orElse(Collections.emptyList())
                .stream()
                .map(o -> {
                    OptionResult option = new OptionResult();
                    option.setOptionId(o.getOptionId());
                    option.setText(o.getText());
                    option.setVotes(o.getVotes());
                    option.setPercentage(o.getPercentage());
                    return option;
                })
                .toList();

        result.addAllOptionResults(optionResults);

        return result;
    }
}



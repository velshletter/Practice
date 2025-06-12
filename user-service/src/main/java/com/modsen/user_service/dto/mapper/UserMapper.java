package com.modsen.user_service.dto.mapper;


import com.modsen.user_service.dto.UserResponse;
import com.modsen.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toDto(User user);

    User toEntity(UserResponse userResponse);
}

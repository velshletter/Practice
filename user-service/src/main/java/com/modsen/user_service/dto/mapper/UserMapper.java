package com.modsen.user_service.dto.mapper;


import com.modsen.user_service.dto.AdminUserResponseDto;
import com.modsen.user_service.dto.UserResponseDto;
import com.modsen.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponseDto toUserDto(User user);

    AdminUserResponseDto toAdminUserDto(User user);
}

package com.modsen.user_service.service;

import com.modsen.user_service.security.CustomUserDetails;
import com.modsen.user_service.dto.ChangePasswordRequestDto;
import com.modsen.user_service.dto.UserResponseDto;
import com.modsen.user_service.dto.UserUpdateRequestDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto getCurrentUser(CustomUserDetails userDetails);

    UserResponseDto updateProfile(UUID userId, UserUpdateRequestDto updateDto);

    void changePassword(UUID userId, ChangePasswordRequestDto changePasswordDto);

    void deleteCurrentUser(UUID userId);

}

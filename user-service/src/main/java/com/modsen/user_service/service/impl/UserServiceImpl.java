package com.modsen.user_service.service.impl;

import com.modsen.user_service.dto.UserResponseDto;
import com.modsen.user_service.security.CustomUserDetails;
import com.modsen.user_service.dto.ChangePasswordRequestDto;
import com.modsen.user_service.dto.UserUpdateRequestDto;
import com.modsen.user_service.dto.mapper.UserMapper;
import com.modsen.user_service.entity.User;
import com.modsen.user_service.exception.UserNotFoundException;
import com.modsen.user_service.repository.UserRepository;
import com.modsen.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getCurrentUser(CustomUserDetails userDetails) {
        User user = userDetails.user();
        log.debug("Current user: {}", user.getEmail());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserResponseDto updateProfile(UUID userId, UserUpdateRequestDto updateDto) {
        User user = fetchUserOrThrow(userId);
        user.setUsername(updateDto.getUsername());
        User updated = userRepository.save(user);
        return userMapper.toUserDto(updated);
    }

    @Override
    public void changePassword(UUID userId, ChangePasswordRequestDto changePasswordDto) {
        User user = fetchUserOrThrow(userId);

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteCurrentUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private User fetchUserOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}

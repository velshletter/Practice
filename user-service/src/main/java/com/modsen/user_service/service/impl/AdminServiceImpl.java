package com.modsen.user_service.service.impl;

import com.modsen.user_service.dto.AdminUserResponseDto;
import com.modsen.user_service.dto.mapper.UserMapper;
import com.modsen.user_service.entity.User;
import com.modsen.user_service.exception.UserNotFoundException;
import com.modsen.user_service.repository.UserRepository;
import com.modsen.user_service.service.AdminService;
import com.modsen.user_service.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<AdminUserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toAdminUserDto)
                .toList();
    }

    @Override
    public AdminUserResponseDto getUserById(UUID userId) {
        User user = fetchUser(userId);
        return userMapper.toAdminUserDto(user);
    }

    @Override
    public AdminUserResponseDto changeUserRole(UUID userId, Role newRole) {
        User user = fetchUser(userId);
        user.setRole(newRole);
        return userMapper.toAdminUserDto(userRepository.save(user));
    }

    @Override
    public AdminUserResponseDto activateUser(UUID userId) {
        User user = fetchUser(userId);
        user.setActive(true);
        return userMapper.toAdminUserDto(userRepository.save(user));
    }

    @Override
    public AdminUserResponseDto deactivateUser(UUID userId) {
        User user = fetchUser(userId);
        user.setActive(false);
        return userMapper.toAdminUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private User fetchUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}

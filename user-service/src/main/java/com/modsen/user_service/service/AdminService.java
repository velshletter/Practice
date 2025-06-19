package com.modsen.user_service.service;

import com.modsen.user_service.dto.AdminUserResponseDto;
import com.modsen.user_service.util.Role;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    List<AdminUserResponseDto> getAllUsers();

    AdminUserResponseDto getUserById(UUID userId);

    AdminUserResponseDto changeUserRole(UUID userId, Role newRole);

    AdminUserResponseDto activateUser(UUID userId);

    AdminUserResponseDto deactivateUser(UUID userId);

    void deleteUser(UUID userId);

}

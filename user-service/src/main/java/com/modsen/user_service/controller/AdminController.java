package com.modsen.user_service.controller;


import com.modsen.user_service.dto.AdminUserResponseDto;
import com.modsen.user_service.service.AdminService;
import com.modsen.user_service.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminUserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUserResponseDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<AdminUserResponseDto> changeRole(@PathVariable UUID id, @RequestParam Role role) {
        return ResponseEntity.ok(adminService.changeUserRole(id, role));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AdminUserResponseDto> activateUser(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.activateUser(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AdminUserResponseDto> deactivateUser(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.deactivateUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

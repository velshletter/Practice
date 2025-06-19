package com.modsen.user_service.controller;


import com.modsen.user_service.dto.AuthRequestDto;
import com.modsen.user_service.dto.AuthResponseDto;
import com.modsen.user_service.dto.RegisterResponseDto;
import com.modsen.user_service.dto.RegisterRequestDto;
import com.modsen.user_service.entity.User;
import com.modsen.user_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        User savedUser = service.saveUser(request);
        RegisterResponseDto response = new RegisterResponseDto("User registered successfully", savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> authenticate(@Valid @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto token = service.authenticateAndGenerateToken(authRequestDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestParam UUID refreshToken) {
        AuthResponseDto response = service.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> revokeToken(@RequestParam UUID refreshToken) {
        service.revokeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return ResponseEntity.ok("Token is valid");
    }
}
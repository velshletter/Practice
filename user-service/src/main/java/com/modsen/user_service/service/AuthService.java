package com.modsen.user_service.service;

import com.modsen.user_service.dto.RegisterRequest;
import com.modsen.user_service.entity.User;
import com.modsen.user_service.repository.UserRepository;
import com.modsen.user_service.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public User saveUser(RegisterRequest dto) {

        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .createdAt(Instant.now())
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}

package com.modsen.user_service.service;

import com.modsen.user_service.dto.AuthRequestDto;
import com.modsen.user_service.dto.AuthResponseDto;
import com.modsen.user_service.dto.RegisterRequestDto;
import com.modsen.user_service.entity.RefreshToken;
import com.modsen.user_service.entity.User;
import com.modsen.user_service.repository.RefreshTokenRepository;
import com.modsen.user_service.repository.UserRepository;
import com.modsen.user_service.security.CustomUserDetails;
import com.modsen.user_service.security.JwtService;
import com.modsen.user_service.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.refresh-token-ttl}")
    private Duration refreshTokenTtl;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User saveUser(RegisterRequestDto dto) {

        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    public AuthResponseDto authenticateAndGenerateToken(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.email(),
                        authRequestDto.password()
                )
        );

        if (!authentication.isAuthenticated())
            throw new BadCredentialsException("Invalid email or password");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plus(refreshTokenTtl));
        refreshTokenRepository.save(refreshToken);

        return new AuthResponseDto(accessToken, refreshToken.getId());
    }

    public AuthResponseDto refreshToken(UUID refreshToken) {
        final var refreshTokenEntity = refreshTokenRepository.findByIdAndExpiresAtAfter(refreshToken, Instant.now())
                .orElseThrow(() -> new BadCredentialsException("Invalid or expired refresh token"));

        final var newAccessToken = jwtService.generateToken(refreshTokenEntity.getUser());
        return new AuthResponseDto(newAccessToken, refreshToken);
    }

    public void revokeRefreshToken(UUID refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}

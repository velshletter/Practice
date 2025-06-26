package com.modsen.gateway_service.config;

import com.modsen.gateway_service.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r
                        .path("/users/**", "/auth/**", "/admin/**", "/user-service/v3/api-docs")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("lb://user-service"))
                .route("poll-service", r -> r
                        .path("/polls/**", "/poll-service/v3/api-docs")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("lb://poll-service"))
                .build();
    }
}

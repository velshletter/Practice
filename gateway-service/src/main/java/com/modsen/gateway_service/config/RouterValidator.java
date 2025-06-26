package com.modsen.gateway_service.config;

import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    private static final List<String> openApiEndpoints = List.of(
            "/auth/**",
            "/user-service/v3/api-docs",
            "/poll-service/v3/api-docs"
    );

    private final PathPatternParser parser = new PathPatternParser();

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .map(parser::parse)
                    .noneMatch(pattern -> pattern.matches(PathContainer.parsePath(request.getPath().value())));
}
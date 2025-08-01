package com.modsen.poll_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PollNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePollNotFoundException(PollNotFoundException ex, WebRequest request) {
        log.warn("Poll not found: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), request, "POLL_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OptionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleOptionNotFoundException(OptionNotFoundException ex, WebRequest request) {
        log.warn("Option not found: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), request, "OPTION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OptionMismatchException.class)
    public ResponseEntity<ErrorDetails> handleOptionMismatchException(OptionMismatchException ex, WebRequest request) {
        log.warn("Option mismatch: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), request, "OPTION_MISMATCH", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyVotedException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyVotedException(UserAlreadyVotedException ex, WebRequest request) {
        log.warn("User already voted: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), request, "USER_ALREADY_VOTED", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PollNotActiveException.class)
    public ResponseEntity<ErrorDetails> handlePollNotActiveException(PollNotActiveException ex, WebRequest request) {
        log.warn("Poll not active: {}", ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), request, "POLL_NOT_ACTIVE", HttpStatus.BAD_REQUEST);
    }

    // Универсальный хендлер для всех остальных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return buildErrorResponse("Internal server error", request, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDetails> buildErrorResponse(String message, WebRequest request, String code, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getDescription(false),
                code
        );
        return new ResponseEntity<>(errorDetails, status);
    }
}

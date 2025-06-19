package com.modsen.user_service.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleNotFoundException(final UserNotFoundException ex, final WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "USER_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        StringBuilder message = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                message.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ")
        );

        return new ResponseEntity<>(
                new ErrorDetails(
                        LocalDateTime.now(),
                        message.toString().trim(),
                        request.getDescription(false),
                        "VALIDATION_ERROR"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorDetails> handleJwtException(JwtException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false),
                        "INVALID_JWT"
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

}

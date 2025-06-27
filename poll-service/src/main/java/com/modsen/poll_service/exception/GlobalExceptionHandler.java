package com.modsen.poll_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Throwable>, ErrorMeta> ERROR_MAP = Map.ofEntries(
            Map.entry(PollNotFoundException.class, new ErrorMeta("POLL_NOT_FOUND", HttpStatus.NOT_FOUND)),
            Map.entry(OptionNotFoundException.class, new ErrorMeta("OPTION_NOT_FOUND", HttpStatus.NOT_FOUND)),
            Map.entry(OptionMismatchException.class, new ErrorMeta("OPTION_MISMATCH", HttpStatus.BAD_REQUEST)),
            Map.entry(UserAlreadyVotedException.class, new ErrorMeta("USER_ALREADY_VOTED", HttpStatus.CONFLICT)),
            Map.entry(PollNotActiveException.class, new ErrorMeta("POLL_NOT_ACTIVE", HttpStatus.BAD_REQUEST))
    );

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDetails> handleAllExceptions(Throwable ex, WebRequest request) {
        ErrorMeta meta = ERROR_MAP.getOrDefault(
                ex.getClass(),
                new ErrorMeta("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR)
        );

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                meta.errorCode()
        );

        return new ResponseEntity<>(errorDetails, meta.status());
    }

    private record ErrorMeta(String errorCode, HttpStatus status) {}
}
package com.modsen.poll_service.exception;

public class PollNotActiveException extends RuntimeException {
    public PollNotActiveException(String message) {
        super(message);
    }
}
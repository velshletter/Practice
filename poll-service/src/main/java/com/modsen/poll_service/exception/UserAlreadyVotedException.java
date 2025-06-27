package com.modsen.poll_service.exception;

public class UserAlreadyVotedException extends RuntimeException {
    public UserAlreadyVotedException(String msg) {
        super(msg);
    }
}
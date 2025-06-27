package com.modsen.poll_service.exception;

public class OptionMismatchException extends RuntimeException {
    public OptionMismatchException(String msg) {
        super(msg);
    }
}
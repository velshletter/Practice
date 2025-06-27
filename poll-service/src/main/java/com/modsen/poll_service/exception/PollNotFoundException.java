package com.modsen.poll_service.exception;

public class PollNotFoundException extends RuntimeException {

    public PollNotFoundException(String msg) {
        super(msg);
    }

}

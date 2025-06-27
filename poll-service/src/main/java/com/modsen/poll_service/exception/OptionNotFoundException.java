package com.modsen.poll_service.exception;

public class OptionNotFoundException extends RuntimeException {

    public OptionNotFoundException(String msg) {
        super(msg);
    }

}
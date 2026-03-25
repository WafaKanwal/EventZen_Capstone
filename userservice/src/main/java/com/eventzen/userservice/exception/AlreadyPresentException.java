package com.eventzen.userservice.exception;

public class AlreadyPresentException extends RuntimeException {
    public AlreadyPresentException(String message) {
        super(message);
    }
}
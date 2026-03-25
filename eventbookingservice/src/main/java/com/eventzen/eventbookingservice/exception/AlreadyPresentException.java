package com.eventzen.eventbookingservice.exception;

public class AlreadyPresentException extends RuntimeException {
    public AlreadyPresentException(String message) {
        super(message);
    }
}
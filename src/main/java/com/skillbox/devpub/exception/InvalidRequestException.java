package com.skillbox.devpub.exception;


public class InvalidRequestException extends RuntimeException {
    private String error;

    public InvalidRequestException(String message) {
        super(message);
//        error = message;
    }
}

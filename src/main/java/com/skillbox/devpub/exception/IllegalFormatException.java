package com.skillbox.devpub.exception;

public class IllegalFormatException extends RuntimeException{

    private static String message;

    public IllegalFormatException(String message) {
        super(message);
    }

    public IllegalFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public static IllegalFormatException create() {
        return new IllegalFormatException(message);
    }
}

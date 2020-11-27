package com.skillbox.devpub.exception;

public class IllegalFormatException extends RuntimeException{

    private static String message;

    public IllegalFormatException(String message) {

        super(message);
    }

    public static IllegalFormatException create() {

        return new IllegalFormatException(message);
    }
}

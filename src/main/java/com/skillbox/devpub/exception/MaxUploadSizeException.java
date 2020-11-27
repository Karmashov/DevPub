package com.skillbox.devpub.exception;

public class MaxUploadSizeException extends RuntimeException {

    private static String message;

    public MaxUploadSizeException(String message) {

        super(message);
    }

    public static MaxUploadSizeException create() {

        return new MaxUploadSizeException(message);
    }
}

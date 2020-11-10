package com.skillbox.devpub.exception;

public class MaxUploadSizeException extends RuntimeException {

    private static String message;

    public MaxUploadSizeException(String message) {
        super(message);
    }

    public MaxUploadSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static MaxUploadSizeException create() {
        return new MaxUploadSizeException(message);
    }
}

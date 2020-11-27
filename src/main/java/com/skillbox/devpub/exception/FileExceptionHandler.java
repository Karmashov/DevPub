package com.skillbox.devpub.exception;

public class FileExceptionHandler {

    private final Boolean result;
    private final String message;

    public FileExceptionHandler(String message) {
        this.result = false;
        this.message = message;
    }

    public Boolean getResult() {

        return result;
    }

    public String getMessage() {

        return message;
    }
}

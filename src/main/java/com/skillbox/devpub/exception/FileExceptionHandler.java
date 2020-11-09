package com.skillbox.devpub.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class FileExceptionHandler {
    private Boolean result;
    private HashMap<String, String> errors;
    private HttpStatus status;

    public FileExceptionHandler(HashMap<String, String> errors, HttpStatus status) {
        this.result = false;
        this.errors = errors;
        this.status = status;
    }

    public Boolean getResult() {
        return result;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}

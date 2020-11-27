package com.skillbox.devpub.exception;

public class EntityNotFoundException extends RuntimeException {

    private static String message;

    public EntityNotFoundException(String message) {

        super(message);
    }

    public static EntityNotFoundException create() {

        return new EntityNotFoundException(message);
    }
}

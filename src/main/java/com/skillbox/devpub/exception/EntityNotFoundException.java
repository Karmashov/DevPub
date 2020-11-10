package com.skillbox.devpub.exception;

public class EntityNotFoundException extends RuntimeException {

    private static String message;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EntityNotFoundException create() {
        return new EntityNotFoundException(message);
    }

//    private EntityNotFoundException(String message) {
//        this.message = "Документ не найден";
//    }
//
//    public static EntityNotFoundException createException() {
//        return new EntityNotFoundException(message);
//    }
}

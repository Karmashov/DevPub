package com.skillbox.devpub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MaxUploadSizeException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeException ex) {

        FileExceptionHandler handler = new FileExceptionHandler(ex.getMessage());

        return new ResponseEntity<>(handler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalFormatException.class)
    public ResponseEntity<Object> handleIllegalFormatException(IllegalFormatException ex) {

        FileExceptionHandler handler = new FileExceptionHandler(ex.getMessage());

        return new ResponseEntity<>(handler, HttpStatus.BAD_REQUEST);
    }
}

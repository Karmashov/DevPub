package com.skillbox.devpub.exception;

import com.skillbox.devpub.dto.universal.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidRequestException.class})
    protected ResponseEntity<?> handleInvalidRequestException(
            InvalidRequestException ex,
            WebRequest request
    ) {
        log.warn(ex.getMessage());
        return handleExceptionInternal(
                ex,
                new ErrorResponse(false, ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}

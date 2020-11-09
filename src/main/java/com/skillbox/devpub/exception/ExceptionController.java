package com.skillbox.devpub.exception;

import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.ErrorListResponse;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {

//        SimpleHandler handler = new SimpleHandler(
////                ex.getMessage(),
////                ex,
//                HttpStatus.NOT_FOUND);
//        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, null);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        HashMap<String, String> error = new HashMap<>();
        error.put("photo", "Фото слишком большое, нужно не более 5 Мб");
        FileExceptionHandler handler = new FileExceptionHandler(error, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(handler, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ResponseFactory.getErrorListResponse(error), HttpStatus.BAD_REQUEST);
//        return handleExceptionInternal(ex, handler, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
    }

//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ModelAndView handleMaxSizeException(
//            MaxUploadSizeExceededException exc,
//            HttpServletRequest request,
//            HttpServletResponse response) {
//
//        ModelAndView modelAndView = new ModelAndView("file");
//        modelAndView.getModel().put("message", "File too large!");
//        return modelAndView;
//    }
}

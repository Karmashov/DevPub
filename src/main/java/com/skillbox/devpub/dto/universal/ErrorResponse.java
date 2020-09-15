package com.skillbox.devpub.dto.universal;

import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
@Data
public class ErrorResponse implements Response {

    private Boolean result;
//    private String errorType;
//    private String errorMessage;

//    public ErrorResponse(Boolean result, String errorType, String errorMessage) {
//        this.result = result;
////        this.errorType = errorType;
////        this.errorMessage = errorMessage;
//    }

    public ErrorResponse(Boolean result) {
        this.result = result;
    }
}

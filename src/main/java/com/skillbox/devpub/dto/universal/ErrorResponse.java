package com.skillbox.devpub.dto.universal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse implements Response {
//    private String error;
    private Boolean result;

    @JsonProperty("error")
    private String errorDescription;


    public ErrorResponse(Boolean result/*, String error*/, String errorDescription){
        this.result = result;
//        this.error = error;
        this.errorDescription = errorDescription;
    }

//    public ErrorResponse() {
//        result = false;
//    }
}

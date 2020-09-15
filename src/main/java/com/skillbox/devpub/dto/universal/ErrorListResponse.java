package com.skillbox.devpub.dto.universal;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorListResponse implements Response {
//    private String error;
    private Boolean result;

//    @JsonProperty("errors")
    private HashMap<String, String> errors;


    public ErrorListResponse(Boolean result/*, String error*/, HashMap<String, String> errors){
        this.result = result;
//        this.error = error;
        this.errors = errors;
    }

}

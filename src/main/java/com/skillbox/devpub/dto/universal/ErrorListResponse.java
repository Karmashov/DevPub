package com.skillbox.devpub.dto.universal;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@NoArgsConstructor
@Data
public class ErrorListResponse implements Response {
    private Boolean result;
    private HashMap<String, String> errors;
    private HttpStatus status;


    public ErrorListResponse(HashMap<String, String> errors) {
        this.result = false;
        this.errors = errors;
        status = HttpStatus.BAD_REQUEST;
    }
}

package com.skillbox.devpub.dto.universal;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@Data
public class ErrorListResponse implements Response {

    private Boolean result;
    private HashMap<String, String> errors;

    public ErrorListResponse(HashMap<String, String> errors) {
        this.result = false;
        this.errors = errors;
    }
}

package com.skillbox.devpub.dto.universal;

import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
@Data
public class ErrorResponse implements Response {

    private Boolean result;

    public ErrorResponse() {
        result = false;
    }
}

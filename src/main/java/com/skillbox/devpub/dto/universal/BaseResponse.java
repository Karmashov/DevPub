package com.skillbox.devpub.dto.universal;

import lombok.Data;

@Data
public class BaseResponse implements Response {

    private Boolean result;

    public BaseResponse(Boolean result) {
        this.result = result;
    }
}

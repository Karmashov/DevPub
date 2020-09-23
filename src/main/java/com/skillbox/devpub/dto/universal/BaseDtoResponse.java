package com.skillbox.devpub.dto.universal;

import lombok.Data;

@Data
public class BaseDtoResponse implements Response {

    private Boolean result;
    private Dto dto;

    public BaseDtoResponse(Boolean result, Dto dto) {
        this.result = result;
        this.dto = dto;
    }
}

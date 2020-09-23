package com.skillbox.devpub.dto.universal;

import lombok.Data;

@Data
public class DtoResponse implements Response {

    private Dto dto;

    public DtoResponse(Dto dto) {
        this.dto = dto;
    }
}

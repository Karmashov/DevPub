package com.skillbox.devpub.dto.universal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class BaseResponse implements Response {
    private Boolean result;
//    private Long timestamp;
//    private Dto data;

    public BaseResponse(Dto data) {
        result = true;
//        timestamp = new Date().getTime();
//        this.data = data;
    }

//    public BaseResponse(){
//        result = true;
//    }
}

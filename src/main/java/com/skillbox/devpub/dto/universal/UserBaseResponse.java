package com.skillbox.devpub.dto.universal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserBaseResponse implements Response {
    private Boolean result;
    //    private Long timestamp;
    private Dto user;

    public UserBaseResponse(Dto user) {
        result = true;
//        timestamp = new Date().getTime();
        this.user = user;
    }
}

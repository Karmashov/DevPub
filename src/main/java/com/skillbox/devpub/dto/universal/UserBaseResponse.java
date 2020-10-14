package com.skillbox.devpub.dto.universal;

import lombok.Data;

@Data
public class UserBaseResponse extends BaseResponse {
    private Dto user;

    public UserBaseResponse(Boolean result, Dto user) {
        super(result);
        this.user = user;
    }
}

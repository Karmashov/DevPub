package com.skillbox.devpub.dto.user;

import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.Dto;
import lombok.Data;

@Data
public class UserBaseResponse extends BaseResponse {

    private Dto user;

    public UserBaseResponse(Boolean result, Dto user) {
        super(result);
        this.user = user;
    }
}

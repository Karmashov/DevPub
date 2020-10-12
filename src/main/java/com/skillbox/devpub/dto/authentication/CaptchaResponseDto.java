package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaResponseDto implements Response {

    private String secret;
    private String image;
}

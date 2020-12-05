package com.skillbox.devpub.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    @JsonProperty("e_mail")
    private String email;
    private String password;
    private String name;
    private String captcha;
    @JsonProperty("captcha_secret")
    private String captchaSecret;
}

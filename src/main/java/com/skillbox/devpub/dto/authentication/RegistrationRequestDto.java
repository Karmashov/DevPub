package com.skillbox.devpub.dto.authentication;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String e_mail;
    private String password;
    private String name;
    private String captcha;
    private String captcha_secret;
}

package com.skillbox.devpub.dto.authentication;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String e_mail;
    private String password;
}

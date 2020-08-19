package com.skillbox.devpub.dto.authentication;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String email;
    private String password;
}

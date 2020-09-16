package com.skillbox.devpub.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationRequestDto {

    @JsonProperty("e_mail")
    private String email;
    private String password;
}

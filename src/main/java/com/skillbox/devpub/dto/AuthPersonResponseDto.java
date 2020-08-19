package com.skillbox.devpub.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthPersonResponseDto extends PersonResponseDto {
    private String token;

    public AuthPersonResponseDto(
            Integer id,
            Boolean isModerator,
            LocalDateTime regTime,
            String name,
            String email,
            String password,
            String code,
            String photo,
            String token
    ) {
        super(id, isModerator, regTime, name, email,
                password, code, photo, token);
        this.token = token;
    }

}

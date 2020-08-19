package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.user.UserResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationUserResponseDto extends UserResponseDto {
    private String token;

    public AuthenticationUserResponseDto(
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

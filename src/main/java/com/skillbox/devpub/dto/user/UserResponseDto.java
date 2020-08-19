package com.skillbox.devpub.dto.user;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements Dto {
    protected Integer id;
    protected Boolean isModerator;
    protected LocalDateTime regTime;
    protected String name;
    protected String email;
    protected String password;
    protected String code;
    protected String photo;
    protected String token;
}

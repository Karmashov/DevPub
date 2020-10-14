package com.skillbox.devpub.dto.user;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements Dto {
    private Integer id;
    private Boolean isModerator;
    private String regTime;
    private String name;
    private String email;
    private String password;
    private String code;
    private String photo;
}

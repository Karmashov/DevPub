package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.Data;

@Data
public class AuthUserResponseDto implements Dto {

    private Integer id;
    private String name;
    private String photo;
    private String email;
    private Boolean moderation;
    private Integer moderationCount;
    private Boolean settings;

    public AuthUserResponseDto(
            Integer id,
            String name,
            String photo,
            String email,
            Boolean moderation,
            Integer moderationCount,
            Boolean settings
    ) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.moderation = moderation;
        this.moderationCount = moderationCount;
        this.settings = settings;
    }
}

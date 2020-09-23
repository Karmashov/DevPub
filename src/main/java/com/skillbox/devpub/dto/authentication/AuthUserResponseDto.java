package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.Data;

@Data
//@EqualsAndHashCode(callSuper = true)
public class AuthUserResponseDto implements Dto/* extends UserResponseDto*/ {

    private Integer id;
    private String name;
    private String photo;
    private String email;
    private Boolean moderation;
    private Integer moderationCount;
    private Boolean settings;
//    private String token;

//    public AuthenticationUserResponseDto(
//            Integer id,
//            String name,
//            String photo,
//            String email,
//            Boolean isModerator,
//            LocalDateTime regTime,
//            String password,
//            String code,
//            String token
//    ) {
//        super(id, isModerator, regTime, name, email,
//                password, code, photo, token);
//        this.token = token;
//    }

    public AuthUserResponseDto(
            Integer id,
            String name,
            String photo,
            String email,
            Boolean moderation,
//            Integer moderationCount,
            Boolean settings
    ) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.moderation = moderation;
        this.moderationCount = 0;
        this.settings = settings;
    }
}

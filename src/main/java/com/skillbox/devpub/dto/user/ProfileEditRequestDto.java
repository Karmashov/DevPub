package com.skillbox.devpub.dto.user;

import lombok.Data;

@Data
public class ProfileEditRequestDto {

    private String name;
    private String email;
    private String password;
    private String removePhoto;
}


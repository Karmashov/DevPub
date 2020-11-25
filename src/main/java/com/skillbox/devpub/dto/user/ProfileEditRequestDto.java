package com.skillbox.devpub.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileEditRequestDto {
    private String name;
    private String email;
    private String password;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private MultipartFile photo;
    private String removePhoto;
}


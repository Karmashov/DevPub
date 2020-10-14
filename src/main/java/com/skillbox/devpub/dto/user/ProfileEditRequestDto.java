package com.skillbox.devpub.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileEditRequestDto {
    private String name;
    private String email;
    private String password;
    private MultipartFile photo;
    private String removePhoto;
}

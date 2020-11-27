package com.skillbox.devpub.dto.universal;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileRequestDto {

    private String name;
    private MultipartFile file;
}

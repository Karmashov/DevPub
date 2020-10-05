package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Response saveFile(MultipartFile file);
}

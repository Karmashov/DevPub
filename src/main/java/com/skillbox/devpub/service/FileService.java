package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException;
}

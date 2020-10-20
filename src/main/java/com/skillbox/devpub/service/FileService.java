package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FileService {
//    String saveFile(MultipartFile file) throws IOException;
    String saveFile(BufferedImage file, String format) throws IOException;

    BufferedImage resize(BufferedImage bufferedImage, int width, int height);
}

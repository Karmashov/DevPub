package com.skillbox.devpub.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FileService {

    String saveFile(BufferedImage file, String format) throws IOException;

    BufferedImage resize(BufferedImage bufferedImage, int width, int height);
}

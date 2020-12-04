package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String saveFile(BufferedImage file, String format) throws IOException {

        String s = UUID.randomUUID().toString();
        String[] hash = s.split("-");
        File fullUploadPath = new File(uploadPath + "/" + hash[0] + "/" + hash[1] + "/" + hash[2]);
        if (!fullUploadPath.exists()) {
            fullUploadPath.mkdirs();
        }

        String newFilename = fullUploadPath + "/" + hash[3] + "." + format;
        File fullFile = new File(newFilename);
        ImageIO.write(file, format, fullFile);

        String fileLink = "/" + fullFile.toString().replaceAll("\\\\", "/");
        System.out.println(fileLink);

        return fileLink;
    }

    @Override
    public BufferedImage resize(BufferedImage bufferedImage, int width, int height) {

        Image tmp = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }
}

package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.universal.FileRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

//    public FileServiceImpl(String uploadPath) {
//        this.uploadPath = uploadPath;
//    }

    @Override
    public String saveFile(MultipartFile file /*FileRequestDto request*/) throws IOException {
        String s = UUID.randomUUID().toString();
//        int random = (int) Math.random() * 134;
        String[] hash = s.split("-");
        File fullUploadPath = new File(uploadPath + "/" + hash[0] + "/" + hash[1] + "/" + hash[2]);
        if (!fullUploadPath.exists()) {
            fullUploadPath.mkdirs();
        }

//        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String fileFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String newFilename = fullUploadPath + "/" + hash[3] + "." + fileFormat;
        File fullFile = new File(newFilename);
        file.transferTo(fullFile);

        String filePath = fullFile.toString().substring(2);

        return filePath;
//        return fullFile.toString();
    }
}

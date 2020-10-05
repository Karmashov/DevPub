package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileServiceImpl implements FileService {

    //@TODO доделать файлы
    @Override
    public Response saveFile(MultipartFile file) {
        String s = String.valueOf(file.hashCode());
        int sLengthSplit = s.length()/3;
        StringBuilder sb = new StringBuilder();
        sb.append(s, 0, sLengthSplit)
                .append("/")
                .append(s, sLengthSplit+1, sLengthSplit * 2)
                .append("/")
                .append(s, sLengthSplit * 2 + 1, s.length())
                .append("/");
        File uploadPath = new File("./upload/" + sb + file.getName());
        System.out.println(uploadPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        return null;
    }
}

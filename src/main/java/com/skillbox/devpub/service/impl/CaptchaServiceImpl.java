package com.skillbox.devpub.service.impl;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.skillbox.devpub.dto.authentication.CaptchaResponseDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.CaptchaCode;
import com.skillbox.devpub.repository.CaptchaCodeRepository;
import com.skillbox.devpub.service.CaptchaService;
import com.skillbox.devpub.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final CaptchaCodeRepository codeRepository;
    private final FileService fileService;
    @Value("${captcha.lifetime}")
    private Integer captchaLifetime;

    public CaptchaServiceImpl(CaptchaCodeRepository codeRepository, FileService fileService) {
        this.codeRepository = codeRepository;
        this.fileService = fileService;
    }

    @Override
    public Response getCaptcha() throws IOException {
        String secret = UUID.randomUUID().toString().replace("-", "");

        Cage cage = new GCage();
        String captchaCode = cage.getTokenGenerator().next();
        BufferedImage bufferedImage = cage.drawImage(captchaCode);
        bufferedImage = fileService.resize(bufferedImage, 100, 35);

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayStream);
        byteArrayStream.flush();

        byte[] imageInByte = byteArrayStream.toByteArray();
        byteArrayStream.close();
        String image = Base64.getEncoder().encodeToString(imageInByte);

        CaptchaCode captcha = new CaptchaCode();
        captcha.setSecretCode(secret);
        captcha.setCode(captchaCode);
        captcha.setTime(LocalDateTime.now());

        //@TODO изменен тип данных в БД
        codeRepository.save(captcha);

        deleteOldCaptcha();

        return new CaptchaResponseDto(secret, "data:image/png;base64, " + image);
    }

    private void deleteOldCaptcha() {
        LocalDateTime time = LocalDateTime.now();
        List<CaptchaCode> result = codeRepository.findAllByTimeIsBefore(time.minusMinutes(captchaLifetime));
        for (CaptchaCode captcha : result) {
            codeRepository.delete(captcha);
        }
    }
}

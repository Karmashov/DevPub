package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.CaptchaResponseDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.service.CaptchaService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

@Service
public class CaptchaServiceImpl implements CaptchaService {


    @Override
    public Response getCaptcha() throws IOException {
        int totalChars = 6;
        int height = 40;
        int width = 150;
        Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
        Random randChars = new Random();
        String imageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, totalChars);
//        System.out.println(imageCode);
        BufferedImage biImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
        int iCircle = 15;
        for (int i = 0; i < iCircle; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
        }
        g2dImage.setFont(fntStyle1);
        for (int i = 0; i < totalChars; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
            if (i % 2 == 0) {
                g2dImage.drawString(imageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(imageCode.substring(i, i + 1), 25 * i, 35);
            }
        }
//        System.out.println("===========");
//        System.out.println(g2dImage);
//        System.out.println("===========");
//        System.out.println(biImage);

        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        biImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        biImage.

        byte[] bytes = biImage.toString().getBytes();

//        encoder.encode(imageCode.getBytes());
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
//        System.out.println(enc.encode(imageCode));

//        OutputStream osImage = response.getOutputStream();
        OutputStream osImage = null;
//
        ImageIO.write(biImage, "png", osImage);
        g2dImage.dispose();
//        HttpSession session = request.getSession();
//        session.setAttribute("captcha_security", imageCode);

        return new CaptchaResponseDto(imageCode, "data:image/png;base64, " + encoder.encodeToString(osImage.toString().getBytes()));
    }
}

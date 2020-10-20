package com.skillbox.devpub.service.impl;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.skillbox.devpub.dto.authentication.CaptchaResponseDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.CaptchaCode;
import com.skillbox.devpub.repository.CaptchaCodeRepository;
import com.skillbox.devpub.service.CaptchaService;
import com.skillbox.devpub.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${captcha.lifetime}")
    private Integer captchaLifetime;

    private final CaptchaCodeRepository codeRepository;
    private final FileService fileService;

    public CaptchaServiceImpl(CaptchaCodeRepository codeRepository, FileService fileService) {
        this.codeRepository = codeRepository;
        this.fileService = fileService;
    }

    @Override
    public Response getCaptcha() throws IOException {
//        int totalChars = 6;
//        int height = 35;
//        int width = 80;
//        Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
//        Random randChars = new Random();
//        String imageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, totalChars);
//        BufferedImage biImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
//        int iCircle = 15;
//        for (int i = 0; i < iCircle; i++) {
//            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
//        }
//        g2dImage.setFont(fntStyle1);
//        for (int i = 0; i < totalChars; i++) {
//            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
//            if (i % 2 == 0) {
//                g2dImage.drawString(imageCode.substring(i, i + 1), 25 * i, 24);
//            } else {
//                g2dImage.drawString(imageCode.substring(i, i + 1), 25 * i, 35);
//            }
//        }

//        File file = biImage;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write( biImage, "png", baos );
//        baos.flush();
//        byte[] imageInByte = baos.toByteArray();
//        baos.close();
////        byte[] fileContent = FileUtils.readFileToByteArray(biImage);
//        String encodedString = Base64.getEncoder().encodeToString(imageInByte);

        String secret = UUID.randomUUID().toString().replace("-", "");
//        System.out.println(s);
        Cage cage = new GCage();
        String captchaCode = cage.getTokenGenerator().next();
        BufferedImage bufferedImage = cage.drawImage(captchaCode);
        bufferedImage = fileService.resize(bufferedImage, 100, 35);
//        File file = new File("D:/upload/captcha.png");

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayStream);
        byteArrayStream.flush();
        byte[] imageInByte = byteArrayStream.toByteArray();
        byteArrayStream.close();
        String image = Base64.getEncoder().encodeToString(imageInByte);

//        OutputStream os = new FileOutputStream("captcha.png", false);
//        try {
//            cage.draw(cage.getTokenGenerator().next(), os);
//        } finally {
//            os.close();
//        }
//        File file = os.write();
//        byte[] bytes = cage.draw(cage.getTokenGenerator().next());
//        String image = Base64.getEncoder().encodeToString(bytes);
//        byte[] fileContent = FileUtils.readFileToByteArray(os);

        CaptchaCode captcha = new CaptchaCode();
        captcha.setSecretCode(secret);
        captcha.setCode(captchaCode);
        captcha.setTime(LocalDateTime.now());

        //@TODO изменен тип данных в БД
        codeRepository.save(captcha);

        deleteOldCaptcha();

//        System.out.println("===========");
//        System.out.println(g2dImage);
//        System.out.println("===========");
//        System.out.println(biImage);

//        Base64.Encoder encoder = Base64.getEncoder();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        biImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        biImage.

//        byte[] bytes = biImage.toString().getBytes();
//
////        encoder.encode(imageCode.getBytes());
//        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
////        System.out.println(enc.encode(imageCode));
//
////        OutputStream osImage = response.getOutputStream();
//        OutputStream osImage = null;
////
//        ImageIO.write(biImage, "png", osImage);
//        g2dImage.dispose();
////        HttpSession session = request.getSession();
////        session.setAttribute("captcha_security", imageCode);

        return new CaptchaResponseDto(secret, "data:image/png;base64, " + image);
    }

    private void deleteOldCaptcha() {
        LocalDateTime time = LocalDateTime.now();
//        System.out.println(time);
////        time.minusMinutes(captchaLifetime);
//        System.out.println(time.minusMinutes(captchaLifetime));
        List<CaptchaCode> result = codeRepository.findAllByTimeIsBefore(time.minusMinutes(captchaLifetime));
        for (CaptchaCode captcha : result) {
            codeRepository.delete(captcha);
        }
    }

//    public BufferedImage resize(BufferedImage image, int width, int height)
//    {
////        BufferedImage tmpImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
////
////        double widthStep = (double) image.getWidth() / (double) width;
////        double heightStep = (double) image.getHeight() / (double) height;
////
////        for (int x = 0; x < width; x++)
////        {
////            for (int y = 0; y < height; y++) {
////                int rgb = image.getRGB((int) Math.ceil(x * widthStep), (int) Math.ceil(y * heightStep));
////                tmpImage.setRGB(x, y, rgb);
////            }
////        }
////        return tmpImage;
//
//
//    }
}

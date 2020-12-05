package com.skillbox.devpub.service.impl;

import com.google.gson.Gson;
import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.user.ProfileEditRequestDto;
import com.skillbox.devpub.model.CaptchaCode;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.repository.CaptchaCodeRepository;
import com.skillbox.devpub.repository.UserRepository;
import com.skillbox.devpub.service.FileService;
import com.skillbox.devpub.service.MailService;
import com.skillbox.devpub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CaptchaCodeRepository captchaRepository;
    private final FileService fileService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CaptchaCodeRepository captchaRepository,
                           FileService fileService,
                           MailService mailService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.captchaRepository = captchaRepository;
        this.fileService = fileService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));

        if (result == null) {
//            log.warn("IN findByEmail - no user found by email: {}", email);
            return null;
        }

//        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public Response register(RegistrationRequestDto requestDto) {
        HashMap<String, String> errors = new HashMap<>();
        checkUserLogin(requestDto.getEmail(), errors);
        checkUserName(requestDto.getName(), errors);
        checkUserPassword(requestDto.getPassword(), errors);
        checkCaptcha(requestDto.getCaptcha(), requestDto.getCaptchaSecret(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setIsModerator(false);
        user.setRegTime(LocalDateTime.now());
        userRepository.save(user);

//        log.info("IN register - user: {} successfully registered", registeredUser);

        mailService.sendWelcomeMessage(user.getEmail(), user.getName());

        return ResponseFactory.responseOk();
    }

    @Override
    public Response changePassword(PasswordChangeRequestDto request) {
        HashMap<String, String> errors = new HashMap<>();
        checkCode(request.getCode(), errors);
        checkUserPassword(request.getPassword(), errors);
        checkCaptcha(request.getCaptcha(), request.getCaptchaSecret(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        User user = userRepository.findByCode(request.getCode());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseFactory.responseOk();
    }

    @Override
    public Response editProfile(String request,
                                ProfileEditRequestDto requestDto,
                                MultipartFile photo,
                                String email,
                                String name,
                                String password,
                                String removePhoto,
                                Principal principal) {
        HashMap<String, String> errors = new HashMap<>();

        ProfileEditRequestDto dto = (request != null) ? new Gson().fromJson(request, ProfileEditRequestDto.class) : requestDto;

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User " + principal.getName() + " not found"));

        if (dto.getPassword() != null) {
            checkUserPassword(dto.getPassword(), errors);
            if (!errors.containsKey("password")) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
        }

        if (photo != null) {
            String fileFormat = fileService.checkPhoto(photo);

            if (!errors.containsKey("photo")) {
                String link = null;
                try {
                    BufferedImage bufferedImage = ImageIO.read(photo.getInputStream());
                    bufferedImage = fileService.resize(bufferedImage, 36, 36);

                    link = fileService.saveFile(bufferedImage, fileFormat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                user.setPhoto(link);
            }
        }

        if (!dto.getName().equals(user.getName())) {
            checkUserName(dto.getName(), errors);

            if (!errors.containsKey("name")) {
                user.setName(dto.getName());
            }
        }

        if (!dto.getEmail().equals(user.getEmail())) {
            checkUserLogin(dto.getEmail(), errors);

            if (!errors.containsKey("email")) {
                user.setEmail(dto.getEmail());
            }
        }

        if (dto.getRemovePhoto() != null && dto.getRemovePhoto().equals("1")) {
            user.setPhoto(null);
        }

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        userRepository.save(user);

        return ResponseFactory.responseOk();
    }

    private void checkUserLogin(String email, HashMap<String, String> errors) {
        if (userRepository.findByEmail(email).isPresent()) {
            errors.put("email", "Данный e-mail уже зарегистрирован");
        }

    }

    private void checkUserName(String name, HashMap<String, String> errors) {
        if (name.length() < 2) {
            errors.put("name", "Имя указано неверно");
        }
    }

    private void checkUserPassword(String password, HashMap<String, String> errors) {
        if (password.length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }
    }

    private void checkCaptcha(String captcha, String captchaSecret, HashMap<String, String> errors) {
        CaptchaCode captchaCode = captchaRepository.findAllBySecretCode(captchaSecret);
        if (!captcha.equals(captchaCode.getCode())) {
            errors.put("captcha", "Код с картинки введён неверно");
        }
    }

    private void checkCode(String code, HashMap<String, String> errors) {
        User user = userRepository.findByCode(code);
        if (user == null) {
            errors.put("code", "Ссылка для восстановления пароля устарела. <a href=\"/login/restore-password\">Запросить ссылку снова</a>");
        }
    }
}

package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.user.ProfileEditRequestDto;
import com.skillbox.devpub.model.CaptchaCode;
import com.skillbox.devpub.model.Role;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ERole;
import com.skillbox.devpub.repository.CaptchaCodeRepository;
import com.skillbox.devpub.repository.RoleRepository;
import com.skillbox.devpub.repository.UserRepository;
import com.skillbox.devpub.service.FileService;
import com.skillbox.devpub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaCodeRepository captchaRepository;
    private final FileService fileService;
    private final CaptchaServiceImpl captchaService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           CaptchaCodeRepository captchaRepository,
                           FileService fileService,
                           CaptchaServiceImpl captchaService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.captchaRepository = captchaRepository;
        this.fileService = fileService;
        this.captchaService = captchaService;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));

        if (result == null) {
            log.warn("IN findByEmail - no user found by email: {}", email);
            return null;
        }

        log.info("IN findByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public User findById(Integer id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by ID: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by ID: {}", result, id);
        return result;
    }

    @Override
    public Response register(RegistrationRequestDto requestDto) {
        HashMap<String, String> errors = new HashMap<>();
//        List<NamedErrorResponse> errors = new ArrayList<>();
        checkUserLogin(requestDto.getEmail(), errors);
        checkUserName(requestDto.getName(), errors);
        checkUserPassword(requestDto.getPassword(), errors);
        checkCaptcha(requestDto.getCaptcha(), requestDto.getCaptchaSecret(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder(12).encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setIsModerator(false);
        user.setRegTime(LocalDateTime.now());
        user.setRoles(getBasePermission());
        //@TODO убрать пермишены лишние, добавить ArrayListы
//        user.setPhoto("/static/default-1.png");

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

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
        user.setPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()));
        userRepository.save(user);

        return ResponseFactory.responseOk();
    }

    @Override
    public Response editProfile(ProfileEditRequestDto request, Principal principal) {
        //@TODO сделать корректное изменение параметров (чтобы не выбивало после смены мейла, чтобы пропускало изменения на свои же и т.д.)
        HashMap<String, String> errors = new HashMap<>();
//        System.out.println(request);
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User " + principal.getName() + " not found"));
//        System.out.println(user.getName());
//        System.out.println(request.getName());
        if (request.getPassword() != null) {
            checkUserPassword(request.getPassword(), errors);
            if (!errors.containsKey("password")) {
                user.setPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()));
            }
        }
        if (request.getPhoto() != null) {
            System.out.println(request.getPhoto().getSize());
            checkPhoto(request.getPhoto(), errors);
            if (!errors.containsKey("photo")) {
                //@TODO сделать ресайз файла
                String link = null;
                try {
                    link = fileService.saveFile(request.getPhoto());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                user.setPhoto(link);
            }
        }
        if (!request.getName().equals(user.getName())) {
            checkUserName(request.getName(), errors);
            if (!errors.containsKey("name")) {
                user.setName(request.getName());
            }
        }
        if (!request.getEmail().equals(user.getEmail())) {
            checkUserLogin(request.getEmail(), errors);
            if (!errors.containsKey("email")) {
                user.setEmail(request.getEmail());
            }
        }
        if (request.getRemovePhoto() != null && request.getRemovePhoto().equals("1")) {
            user.setPhoto(null);
        }
        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

//        if (request.getPassword() == null && request.getPhoto() == null && request.getRemovePhoto() == null) {
//            checkUserName(request.getName(), errors);
//            checkUserLogin(request.getEmail(), errors);
//            if (!errors.isEmpty()) {
//                return ResponseFactory.getErrorListResponse(errors);
//            }
//            user.setName(request.getName());
//            user.setEmail(request.getEmail());
//        }
//        if (request.getPhoto() == null && request.getRemovePhoto() == null) {
//            checkUserName(request.getName(), errors);
//            checkUserLogin(request.getEmail(), errors);
//            checkUserPassword(request.getPassword(), errors);
//            if (!errors.isEmpty()) {
//                return ResponseFactory.getErrorListResponse(errors);
//            }
//            user.setName(request.getName());
//            user.setEmail(request.getEmail());
//            user.setPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()));
//        }
//        if (request.getRemovePhoto() == null || request.getRemovePhoto().equals("0")) {
//            checkUserName(request.getName(), errors);
//            checkUserLogin(request.getEmail(), errors);
////            checkUserPassword(request.getPassword(), errors);
////            checkPhoto(request.getPhoto(), errors);
//            System.out.println(request.getPhoto());
//            if (!errors.isEmpty()) {
//                return ResponseFactory.getErrorListResponse(errors);
//            }
//            user.setName(request.getName());
//            user.setEmail(request.getEmail());
////            user.setPassword(new BCryptPasswordEncoder(12).encode(request.getPassword()));
////            BufferedImage bufferedImage = null;
//            try {
////                BufferedImage bufferedImage = ImageIO.read((ImageInputStream) request.getPhoto());
////                File file = null;
////                bufferedImage = captchaService.resize(bufferedImage, 35, 35);
////                ImageIO.write(bufferedImage, "jpg", file);
//                String link = fileService.saveFile(request.getPhoto());
//                System.out.println(link);
//                user.setPhoto(link);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (request.getPassword() == null) {
//            if (request.getRemovePhoto().equals("1")) {
//                user.setPhoto(null);
//            }
//        }
        userRepository.save(user);
        return ResponseFactory.responseOk();
    }

    private void checkPhoto(MultipartFile photo, HashMap<String, String> errors) {
        if (photo.getSize() > 1048576) {
            errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
        }
    }

    private List<Role> getBasePermission() {
        Role roleUser = roleRepository.findByName(ERole.ROLE_USER);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        return userRoles;
    }

    private void checkUserLogin(String email, HashMap<String, String> errors)/* throws RuntimeException */{
        if (userRepository.findByEmail(email).isPresent()) {
//            errors.add(new NamedErrorResponse("email", "Данный e-mail уже зарегистрирован"));
            errors.put("email", "Данный e-mail уже зарегистрирован");
//            throw new InvalidRequestException("Данный e-mail уже зарегистрирован");
        }

    }

    private void checkUserName(String name, HashMap<String, String> errors) {
        if (name.length() < 2) {
//            errors.add(new NamedErrorResponse("name", "Имя указано неверно"));
            errors.put("name", "Имя указано неверно");
        }
    }

    private void checkUserPassword(String password, HashMap<String, String> errors) {
        if (password.length() < 6) {
//            errors.add(new NamedErrorResponse("password", "Пароль короче 6-ти символов"));
            errors.put("password", "Пароль короче 6-ти символов");
//            throw new InvalidRequestException("Пароль короче 6-ти символов");
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

package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.exception.InvalidRequestException;
import com.skillbox.devpub.model.Role;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ERole;
import com.skillbox.devpub.repository.RoleRepository;
import com.skillbox.devpub.repository.UserRepository;
import com.skillbox.devpub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);

        if (result == null) {
            log.warn("IN findById - no user found by ID: {}", email);
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
        checkUserLogin(requestDto.getE_mail());
        checkUserName(requestDto.getName());
        checkUserPassword(requestDto.getPassword());
        checkCaptcha(requestDto.getCaptcha(), requestDto.getCaptcha_secret());

        User user = new User();
        user.setEmail(requestDto.getE_mail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setName(requestDto.getName());
        user.setIsModerator(false);
        user.setRegTime(LocalDateTime.now());
        user.setRoles(getBasePermission());
        user.setPhoto("/static/default-1.png");
        //@TODO код?
//        user.setCode();

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return ResponseFactory.responseOk();
    }

    private List<Role> getBasePermission() {
        Role roleUser = roleRepository.findByName(ERole.ROLE_USER);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        return userRoles;
    }

    private void checkUserLogin(String email) throws RuntimeException {
        if (userRepository.findByEmail(email) != null) {
            throw new InvalidRequestException("Данный e-mail уже зарегистрирован");
        }
    }

    //@TODO проверка имени пользователя
    private void checkUserName(String name) {

    }

    private void checkUserPassword(String password) {
        if (password.length() < 6) {
            throw new InvalidRequestException("Пароль короче 6-ти символов");
        }
    }

    //@TODO проверка каптчи
    private void checkCaptcha(String captcha, String captchaSecret) {

    }
}

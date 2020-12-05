package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.EmailRequestDto;
import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.repository.GlobalSettingsRepository;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.CaptchaService;
import com.skillbox.devpub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/auth")
public class ApiAuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;
    private final UserService userService;
    private final GlobalSettingsRepository settingsRepository;

    @Autowired
    public ApiAuthController(AuthService authService,
                             CaptchaService captchaService,
                             UserService userService,
                             GlobalSettingsRepository settingsRepository) {
        this.authService = authService;
        this.captchaService = captchaService;
        this.userService = userService;
        this.settingsRepository = settingsRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @GetMapping("/check")
    public ResponseEntity<?> authCheck(Principal principal) {
        return ResponseEntity.ok(authService.authCheck(principal));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(ResponseFactory.responseOk());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto requestDto) {
        if (settingsRepository.findByCode("MULTIUSER_MODE").getValue().equals("NO")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userService.register(requestDto));
    }

    @GetMapping("/captcha")
    public ResponseEntity<?> getCaptcha() throws IOException {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }

    @PostMapping("/restore")
    public ResponseEntity<?> restorePassword(HttpServletRequest servletRequest,
                                             @RequestBody EmailRequestDto request) {
        String link = servletRequest
                .getScheme() + "://"
                + servletRequest.getServerName() + ":"
                + servletRequest.getServerPort() + "/login/change-password/";

        return ResponseEntity.ok(authService.passwordRecovery(request, link));
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequestDto request) {
        return ResponseEntity.ok(authService.changePassword(request));
    }
}

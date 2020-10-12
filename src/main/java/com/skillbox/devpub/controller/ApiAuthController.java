package com.skillbox.devpub.controller;

import com.mysql.cj.protocol.Protocol;
import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/auth")
public class ApiAuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Autowired
    public ApiAuthController(AuthService authService, CaptchaService captchaService)
    {
        this.authService = authService;
        this.captchaService = captchaService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(/*HttpServletRequest request,*/
                                   @RequestBody AuthRequestDto requestDto/*,
                                   @RequestHeader(name = "Referer", required = false) String referer*/) {
//        Response login = authService.login(requestDto/*, request*//*, referer*/);
//        if (login.getClass().equals(ErrorResponse.class)) {
//                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(login);
//            }

//        System.out.println(request);
//        System.out.println(referer);
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @GetMapping("/check")
    public ResponseEntity<?> authCheck(Principal principal/* HttpServletRequest request*/) {
//        System.out.println(token);
//        System.out.println(request.getSession().getId());
        return ResponseEntity.ok(authService.authCheck(principal));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
//        authService.logout(request);
        return ResponseEntity.ok(ResponseFactory.responseOk());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }

    @GetMapping("/captcha")
    public ResponseEntity<?> getCaptcha() throws IOException {
//        System.out.println();
        return ResponseEntity.ok(captchaService.getCaptcha());
    }
}

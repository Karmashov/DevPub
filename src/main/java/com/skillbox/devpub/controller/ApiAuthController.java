package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.ErrorResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;

@RestController
@RequestMapping(value = "/api/auth")
public class ApiAuthController {

    private final AuthenticationService authService;

    @Autowired
    public ApiAuthController(AuthenticationService authService)
    {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   @RequestBody AuthenticationRequestDto requestDto,
                                   @RequestHeader(name = "Referer", required = false) String referer) {
        Response login = authService.login(requestDto, request/*, referer*/);
        if (login.getClass().equals(ErrorResponse.class)) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(login);
            }

//        System.out.println(request);
//        System.out.println(referer);
        return ResponseEntity.ok(login);
    }

    @GetMapping(value = "/check")
    public ResponseEntity<?> authCheck(HttpServletRequest request) {
//        System.out.println(token);
//        System.out.println(request.getSession().getId());
        return ResponseEntity.ok(authService.authCheck(request));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ResponseFactory.responseOk());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto requestDto, HttpServletRequest request) {
        Response result = authService.register(requestDto, request);
        return ResponseEntity.ok(result);
    }
}

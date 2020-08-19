package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.AuthenticationRequestDto;
import com.skillbox.devpub.dto.Response;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.security.jwt.JwtAuthenticationException;
import com.skillbox.devpub.security.jwt.JwtTokenProvider;
import com.skillbox.devpub.service.AuthenticationService;
import com.skillbox.devpub.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        Response login = authService.login(requestDto, request, referer);
//            if (login.getClass().equals(ErrorResponse.class)) {
//                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(login);
//            }

        return ResponseEntity.ok(login);

    }
}

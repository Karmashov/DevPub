package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        Response login = authService.login(requestDto/*, request, referer*/);
//            if (login.getClass().equals(ErrorResponse.class)) {
//                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(login);
//            }

        return ResponseEntity.ok(login);

    }
}

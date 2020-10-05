package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    @Autowired
    public ApiAuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
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

    @GetMapping(value = "/check")
    public ResponseEntity<?> authCheck(Principal principal/* HttpServletRequest request*/) {
//        System.out.println(token);
//        System.out.println(request.getSession().getId());
        return ResponseEntity.ok(authService.authCheck(principal));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
//        authService.logout(request);
        return ResponseEntity.ok(ResponseFactory.responseOk());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }
}

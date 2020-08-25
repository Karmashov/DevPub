package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.authentication.AuthenticationResponseFactory;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.security.jwt.JwtTokenProvider;
import com.skillbox.devpub.service.AuthenticationService;
import com.skillbox.devpub.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Response login(AuthenticationRequestDto request/*, HttpServletRequest httpServletRequest, String referer*/) {
        String userEmail = request.getE_mail();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, request.getPassword()));
            User user = userService.findByEmail(userEmail);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + userEmail + " not found");
            }

            String token = jwtTokenProvider.createToken(userEmail);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", userEmail);
            response.put("token", token);

            return AuthenticationResponseFactory.getAuthResponse(user, token);

        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

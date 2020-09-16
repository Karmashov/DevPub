package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.authentication.AuthenticationResponseFactory;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.ErrorResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.security.jwt.JwtTokenProvider;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private Map<String, Integer> loggedIn = new HashMap<>();

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Response login(AuthenticationRequestDto request, HttpServletRequest httpServletRequest/*, String referer*/) {
        try {
            String userEmail = request.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, request.getPassword()));
            User user = userService.findByEmail(userEmail);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + userEmail + " not found");
//                return AuthenticationResponseFactory.getErrorResponse();
            }

            String token = jwtTokenProvider.createToken(userEmail);

            loggedIn.put(httpServletRequest.getSession().getId(), user.getId());

//            Map<Object, Object> response = new HashMap<>();
//            response.put("username", userEmail);
//            response.put("token", token);

            return AuthenticationResponseFactory.getAuthResponse(user/*, token*/);

        } catch (AuthenticationException exception) {
            return new ErrorResponse();
//            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public Response authCheck(HttpServletRequest httpServletRequest) {
        User user = getAuthUser(httpServletRequest);
        if (user != null) {
            return AuthenticationResponseFactory.getAuthResponse(user);
        }
        return  new ErrorResponse();
//        String sessionId = httpServletRequest.getSession().getId();
//        if (loggedIn.containsKey(sessionId))
//        {
//            User user = userService.findById(loggedIn.get(sessionId));
//
//            return AuthenticationResponseFactory.getAuthResponse(user);
//        }
//
//        return new ErrorResponse(false);
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        String sessionId = httpServletRequest.getSession().getId();
        if (loggedIn.containsKey(sessionId)) {
            loggedIn.remove(sessionId);
        }
    }

    @Override
    public Response register(RegistrationRequestDto requestDto) {
        Response registration = userService.register(requestDto);
//        User user = userService.findByEmail(requestDto.getE_mail());
//        loggedIn.put(httpServletRequest.getSession().getId(), user.getId());
        return registration;
    }

    @Override
    public User getAuthUser(HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        if (loggedIn.containsKey(sessionId))
        {
            User user = userService.findById(loggedIn.get(sessionId));

            return user;
        }

        return null;
    }
}

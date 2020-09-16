package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    Response login(AuthenticationRequestDto request, HttpServletRequest httpServletRequest/*, String referer*/);

    Response authCheck(HttpServletRequest httpServletRequest);

    void logout(HttpServletRequest httpServletRequest);

    Response register(RegistrationRequestDto requestDto);

    User getAuthUser(HttpServletRequest servletRequest);
}

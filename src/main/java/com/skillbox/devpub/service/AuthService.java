package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface AuthService {

    Response login(AuthRequestDto request/*, HttpServletRequest httpServletRequest*//*, String referer*/);

    Response authCheck(Principal principal/* HttpServletRequest httpServletRequest*/);

//    void logout(HttpServletRequest httpServletRequest);

    Response register(RegistrationRequestDto requestDto);

    User getAuthUser(Principal principal);
}

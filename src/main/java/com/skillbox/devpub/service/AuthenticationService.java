package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.AuthenticationRequestDto;
import com.skillbox.devpub.dto.universal.Response;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    Response login(AuthenticationRequestDto request, HttpServletRequest httpServletRequest, String referer);

}

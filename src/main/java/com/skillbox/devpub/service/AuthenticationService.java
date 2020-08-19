package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.AuthenticationRequestDto;
import com.skillbox.devpub.dto.Response;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    Response login(AuthenticationRequestDto request, HttpServletRequest httpServletRequest, String referer);

}

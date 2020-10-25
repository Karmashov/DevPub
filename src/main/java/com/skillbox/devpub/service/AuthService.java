package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.AuthRequestDto;
import com.skillbox.devpub.dto.authentication.EmailRequestDto;
import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;

import java.security.Principal;

public interface AuthService {

    Response login(AuthRequestDto request);

    Response authCheck(Principal principal);

//    Response register(RegistrationRequestDto requestDto);

    Response passwordRecovery(EmailRequestDto request, String link);

    Response changePassword(PasswordChangeRequestDto request);
}

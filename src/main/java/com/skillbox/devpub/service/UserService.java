package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;

public interface UserService {

    Response register(RegistrationRequestDto requestDto);

    User findByEmail(String email);

    User findById(Integer id);
}

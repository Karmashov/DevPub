package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.authentication.PasswordChangeRequestDto;
import com.skillbox.devpub.dto.authentication.RegistrationRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.user.ProfileEditRequestDto;
import com.skillbox.devpub.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface UserService {

    Response register(RegistrationRequestDto requestDto);

    User findByEmail(String email);

    Response changePassword(PasswordChangeRequestDto request);

    Response editProfile(String request, ProfileEditRequestDto requestDto, MultipartFile photo, String email, String name, String password, String removePhoto, Principal principal);
}

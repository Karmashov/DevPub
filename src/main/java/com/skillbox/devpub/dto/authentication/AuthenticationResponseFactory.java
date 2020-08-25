package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.model.User;

public class AuthenticationResponseFactory {

    public static BaseResponse getAuthResponse(User user, String token) {
        return ResponseFactory.getBaseResponse(
                new AuthenticationUserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getPhoto(),
                        user.getEmail(),
                        user.getIsModerator()
//                        Integer moderationCount,
//                        Boolean settings
//
//                        user.getId(),
//                        user.getName(),
//                        user.getPhoto(),
//                        user.getEmail(),
//                        user.getIsModerator(),
//                        user.getRegTime(),
//                        user.getPassword(),
//                        user.getCode(),
//                        token
                )
        );
    }
}

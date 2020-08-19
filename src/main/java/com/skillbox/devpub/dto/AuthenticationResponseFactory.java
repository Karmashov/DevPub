package com.skillbox.devpub.dto;

import com.skillbox.devpub.model.User;

public class AuthenticationResponseFactory {

    public static BaseResponse getAuthResponse(User user, String token) {
        return ResponseFactory.getBaseResponse(
                new AuthPersonResponseDto(
                        user.getId(),
                        user.getIsModerator(),
                        user.getRegTime(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getCode(),
                        user.getPhoto(),
                        token
                )
        );
    }
}

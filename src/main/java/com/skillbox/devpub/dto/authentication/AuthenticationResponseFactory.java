package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.ErrorResponse;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.universal.UserBaseResponse;
import com.skillbox.devpub.model.User;

public class AuthenticationResponseFactory {

    public static UserBaseResponse getAuthResponse(User user, String token) {
        Boolean settings = true;
        if (!user.getIsModerator()) {
            settings = false;
        }
        return ResponseFactory.getUserBaseResponse(
                new AuthenticationUserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getPhoto(),
                        user.getEmail(),
                        user.getIsModerator(),
                        settings
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

    public static ErrorResponse getErrorResponse() {
        return ResponseFactory.getErrorResponse();
    }
}

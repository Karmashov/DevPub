package com.skillbox.devpub.dto.authentication;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.model.User;

public class AuthResponseFactory {

    public static Response getAuthResponse(Boolean result, User user, int moderationCount) {
        boolean settings = true;

        if (!user.getIsModerator()) {
            settings = false;
        }

        return ResponseFactory.getUserBaseResponse(result,
                new AuthUserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getPhoto(),
                        user.getEmail(),
                        user.getIsModerator(),
                        moderationCount,
                        settings
                )
        );
    }
}

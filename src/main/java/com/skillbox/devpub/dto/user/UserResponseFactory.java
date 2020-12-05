package com.skillbox.devpub.dto.user;

import com.skillbox.devpub.dto.comment.CommentAuthorResponseDto;
import com.skillbox.devpub.dto.post.PostAuthorDto;
import com.skillbox.devpub.model.User;

public class UserResponseFactory {

    public static PostAuthorDto getPostAuthor(User user) {
        return new PostAuthorDto(user.getId(), user.getName());
    }

    public static CommentAuthorResponseDto getCommentAuthor(User user) {
        return new CommentAuthorResponseDto(user.getId(), user.getName(), user.getPhoto());
    }
}

package com.skillbox.devpub.dto.user;

import com.skillbox.devpub.dto.comment.CommentAuthorDto;
import com.skillbox.devpub.dto.post.PostAuthorDto;
import com.skillbox.devpub.model.User;

public class UserResponseFactory {
    public static PostAuthorDto getPostAuthor(User user) {
        return new PostAuthorDto(user.getId(), user.getName());
    }

    public static CommentAuthorDto getCommentAuthor(User user) {
        return new CommentAuthorDto(user.getId(), user.getName(), user.getPhoto());
    }

    private static PostAuthorDto getPostAuthorDto(User user) {
        return new PostAuthorDto(user.getId(), user.getName());
    }

//    private static CommentAuthorDto getCommentAuthorDto(User user) {
//        return new CommentAuthorDto(user.getId(), user.getName(), user.getPhoto());
//    }
}

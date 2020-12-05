package com.skillbox.devpub.dto.comment;

import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.PostComment;

import java.util.ArrayList;
import java.util.List;

public class CommentResponseFactory {

    public static CommentResponseDto getCommentDto(PostComment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getTime(),
                comment.getText(),
                UserResponseFactory.getCommentAuthor(comment.getUser())
        );
    }

    public static CommentResponseDto getSingleCommentDto(PostComment comment) {
        return new CommentResponseDto(
                comment.getId(),
                null, null, null
        );
    }

    public static List<Dto> getCommentList(List<PostComment> commentList) {
        List<Dto> list = new ArrayList<>();
        if (commentList == null) {
            return list;
        }

        for (PostComment comment : commentList) {
            list.add(CommentResponseFactory.getCommentDto(comment));
        }

        return list;
    }
}

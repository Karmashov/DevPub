package com.skillbox.devpub.dto.post;

import com.skillbox.devpub.dto.comment.CommentResponseFactory;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.PostVote;
import com.skillbox.devpub.model.Tag;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PostResponseFactory {
    public static PostResponseDto getSinglePost(Post post) {
//        System.out.println(ResponseFactory.getBaseResponse(postToDto(post)));
        return postToDto(post);
    }

    private static PostResponseDto postToDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTime(),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                post.getText(),
                post.getVotes().stream().filter(v -> v.getValue() > 0).map(PostVote::getValue).collect(Collectors.toList()).size(),
                post.getVotes().stream().filter(v -> v.getValue() < 0).map(PostVote::getValue).collect(Collectors.toList()).size(),
                post.getComments().size(),
                post.getViewCount(),
                CommentResponseFactory.getCommentList(post.getComments(), null),
                post.getTags() != null
                        ? post.getTags()
                        .stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
                        : new ArrayList<>()
        );
    }
}

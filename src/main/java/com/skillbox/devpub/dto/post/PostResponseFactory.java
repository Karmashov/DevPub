package com.skillbox.devpub.dto.post;

import com.skillbox.devpub.dto.comment.CommentResponseFactory;
import com.skillbox.devpub.dto.universal.*;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.PostVote;
import com.skillbox.devpub.model.Tag;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponseFactory {

    public static Response getSinglePost(Post post) {
//        System.out.println(ResponseFactory.getBaseResponse(postToDto(post)));
//        return ResponseFactory.getDtoResponse(postToDto(post));
//        return ResponseFactory.getSinglePostResponse(postToDto(post));
        return postToDto(post);
    }

    private static PostResponseDto postToListDto(Post post) {
//        for (PostComment comment : post.getComments()) {
//            System.out.println(comment);
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new PostResponseDto(
                post.getId(),
                post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                post.getText(),
                null,
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

    private static PostResponseDto postToDto(Post post) {
//        for (PostComment comment : post.getComments()) {
//            System.out.println(comment);
//        }
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new PostResponseDto(
                post.getId(),
                post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                null,
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

    public static BaseResponseList getPostsListWithLimit(List<Post> result, Integer offset, Integer limit) {
        List<Dto> postsDto = new ArrayList<>();
        for (Post post : result) {
            postsDto.add(PostResponseFactory.postToListDto(post));

//            if (post.getTime().before(new Date()))
//                postsDto.add(PostResponseFactory.postToDto(post, person));
        }

        return ResponseFactory.getBaseResponseListWithLimit(postsDto, offset, limit);
    }
}

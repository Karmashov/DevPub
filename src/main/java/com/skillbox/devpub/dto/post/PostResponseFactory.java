package com.skillbox.devpub.dto.post;

import com.skillbox.devpub.dto.comment.CommentResponseFactory;
import com.skillbox.devpub.dto.universal.BaseResponseList;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.PostVote;
import com.skillbox.devpub.model.Tag;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponseFactory {

    public static Response getSinglePost(Post post) {
        return postToDto(post);
    }

    //@TODO очистка текста от html тэгов?
    private static PostResponseDto postToListDto(Post post) {
        return new PostResponseDto(
                post.getId(),
//                post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                Timestamp.valueOf(post.getTime()).getTime(),
                post.getTime().toEpochSecond(ZoneOffset.UTC),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                post.getText(),
                null,
                (int) post.getVotes().stream().filter(v -> v.getValue() > 0).map(PostVote::getValue).count(),
                (int) post.getVotes().stream().filter(v -> v.getValue() < 0).map(PostVote::getValue).count(),
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
        return new PostResponseDto(
                post.getId(),
//                post.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                Timestamp.valueOf(post.getTime()).getTime(),
                post.getTime().toEpochSecond(ZoneOffset.UTC),
//                post.getTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                null,
                post.getText(),
                (int) post.getVotes().stream().filter(v -> v.getValue() > 0).map(PostVote::getValue).count(),
                (int) post.getVotes().stream().filter(v -> v.getValue() < 0).map(PostVote::getValue).count(),
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
        }

        return ResponseFactory.getBaseResponseListWithLimit(postsDto, offset, limit);
    }
}

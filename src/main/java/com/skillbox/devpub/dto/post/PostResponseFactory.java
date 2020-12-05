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
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponseFactory {

    public static Response getSinglePost(Post post) {
        return postToDto(post);
    }

    private static PostResponseDto postToListDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTime(),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                Jsoup.parse(post.getText()).text().trim(),
                null,
                (int) post.getVotes().stream().filter(v -> v.getValue() > 0).map(PostVote::getValue).count(),
                (int) post.getVotes().stream().filter(v -> v.getValue() < 0).map(PostVote::getValue).count(),
                post.getComments().size(),
                post.getViewCount(),
                CommentResponseFactory.getCommentList(post.getComments()),
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
                post.getTime(),
                UserResponseFactory.getPostAuthor(post.getUser()),
                post.getTitle(),
                null,
                post.getText(),
                (int) post.getVotes().stream().filter(v -> v.getValue() > 0).map(PostVote::getValue).count(),
                (int) post.getVotes().stream().filter(v -> v.getValue() < 0).map(PostVote::getValue).count(),
                post.getComments().size(),
                post.getViewCount(),
                CommentResponseFactory.getCommentList(post.getComments()),
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

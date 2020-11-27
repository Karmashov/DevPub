package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.comment.CommentRequestDto;
import com.skillbox.devpub.dto.comment.CommentResponseFactory;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.exception.EntityNotFoundException;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.PostComment;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import com.skillbox.devpub.repository.PostCommentRepository;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.service.CommentService;
import com.skillbox.devpub.service.PostService;
import com.skillbox.devpub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostCommentRepository postCommentRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(PostCommentRepository postCommentRepository, UserService userService, PostService postService, PostRepository postRepository) {
        this.postCommentRepository = postCommentRepository;
        this.userService = userService;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Override
    public Response postComment(CommentRequestDto request, Principal principal) {

        Post post = postRepository.findByIdAndIsActiveAndModerationStatusAndTimeBefore(
                request.getPost(),
                true,
                ModerationStatus.ACCEPTED,
                LocalDateTime.now())
                .orElseThrow(EntityNotFoundException::create);

        PostComment parentComment = null;

        if (request.getParentComment() != null) {
            parentComment = postCommentRepository.findPostCommentById(
                    request.getParentComment())
                    .orElseThrow(EntityNotFoundException::create);
        }

        if (request.getText().length() < 10) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("text", "Текст комментария не задан или слишком короткий");

            return ResponseFactory.getErrorListResponse(errors);
        }

        PostComment comment = new PostComment();
        comment.setParentComment(parentComment);
        comment.setPost(post);
        comment.setUser(userService.findByEmail(principal.getName()));
        comment.setTime(LocalDateTime.now());
        comment.setText(request.getText());
        comment.setChildComments(new ArrayList<>());
        postCommentRepository.save(comment);

        return CommentResponseFactory.getSingleCommentDto(comment);
    }
}

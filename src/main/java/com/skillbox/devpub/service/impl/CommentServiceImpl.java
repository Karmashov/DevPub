package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.comment.CommentRequestDto;
import com.skillbox.devpub.dto.comment.CommentResponseFactory;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.model.PostComment;
import com.skillbox.devpub.repository.PostCommentRepository;
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

    @Autowired
    public CommentServiceImpl(PostCommentRepository postCommentRepository, UserService userService, PostService postService) {
        this.postCommentRepository = postCommentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public Response postComment(CommentRequestDto request, Principal principal) {
        //@TODO обработка не найденных записей поста и/или коммкнтария
        if (request.getText().length() < 10) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("text", "Текст комментария не задан или слишком короткий");
            return ResponseFactory.getErrorListResponse(errors);
        }
        PostComment comment = new PostComment();
        comment.setParentComment(postCommentRepository.findPostCommentById(request.getParentComment()));
        comment.setPost(postService.findById(request.getPost()));
        comment.setUser(userService.findByEmail(principal.getName()));
        comment.setTime(LocalDateTime.now());
        comment.setText(request.getText());
        comment.setChildComments(new ArrayList<>());
        postCommentRepository.save(comment);

        return CommentResponseFactory.getSingleCommentDto(comment);
    }
}

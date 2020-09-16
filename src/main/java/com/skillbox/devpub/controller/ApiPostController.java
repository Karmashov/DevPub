package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.post.AddPostRequestDto;
import com.skillbox.devpub.dto.universal.ErrorResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.PostService;
import com.skillbox.devpub.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/post")
public class ApiPostController {
    private final PostService postService;
    private final AuthService authService;

    @Autowired
    public ApiPostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody AddPostRequestDto requestDto,
                                     HttpServletRequest servletRequest) {
//        System.out.println(requestDto);
        User user = authService.getAuthUser(servletRequest);
        if (user != null) {
//            System.out.println(user);
            return ResponseEntity.ok(postService.addPost(requestDto, user.getId()));
        }
        return ResponseEntity.ok(new ErrorResponse());
    }
}

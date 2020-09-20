package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.universal.ErrorResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> addPost(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest servletRequest) {
//        System.out.println(requestDto);
        User user = authService.getAuthUser(servletRequest);
        if (user != null) {
//            System.out.println(user);
            return ResponseEntity.ok(postService.addPost(requestDto, user));
        }
        return ResponseEntity.ok(new ErrorResponse());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editPost(@PathVariable Integer id,
                                      @RequestBody PostRequestDto requestDto,
                                      HttpServletRequest servletRequest) {
        User user = authService.getAuthUser(servletRequest);
        if (user != null) {
            return ResponseEntity.ok(postService.editPost(requestDto, id, user));
        }
        return ResponseEntity.ok(new ErrorResponse());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id) {
//        Response result = postService.getPost(id);
        return ResponseEntity.ok(postService.getPost(id));
    }
}

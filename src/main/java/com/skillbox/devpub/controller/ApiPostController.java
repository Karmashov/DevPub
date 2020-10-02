package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.vote.VoteRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
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
    public ResponseEntity<?> getPosts(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                      @RequestParam(required = false, defaultValue = "20") Integer limit,
                                      @RequestParam(required = false) String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
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
        return ResponseEntity.ok(new BaseResponse(false));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editPost(@PathVariable Integer id,
                                      @RequestBody PostRequestDto requestDto,
                                      HttpServletRequest servletRequest) {
        User user = authService.getAuthUser(servletRequest);
        if (user != null) {
            return ResponseEntity.ok(postService.editPost(requestDto, id, user));
        }
        return ResponseEntity.ok(new BaseResponse(false));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id) {
//        Response result = postService.getPost(id);
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping
    public ResponseEntity<?> postLike(@RequestBody VoteRequestDto request) {
        return ResponseEntity.ok();
    }
}

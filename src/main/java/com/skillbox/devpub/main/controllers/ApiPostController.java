package com.skillbox.devpub.main.controllers;

import com.skillbox.devpub.main.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {
    @Autowired
    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/post")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }
}

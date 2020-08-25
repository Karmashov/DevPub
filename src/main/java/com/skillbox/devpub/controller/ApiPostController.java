package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.service.PostService;
import com.skillbox.devpub.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/post")
public class ApiPostController {
    private final PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestParam(name = "time") LocalDateTime time,
                                     @RequestParam(name = "active") Boolean active,
                                     @RequestParam(name = "title") String title,
                                     @RequestParam(name = "tags") List<Tag> tags,
                                     @RequestParam(name = "text") String text) {
        return ResponseEntity.ok(postService.addPost(time, active, title, tags, text));
    }
}

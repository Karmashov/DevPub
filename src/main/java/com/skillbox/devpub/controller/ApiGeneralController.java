package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.comment.CommentRequestDto;
import com.skillbox.devpub.dto.post.PostModerationRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.FileRequestDto;
import com.skillbox.devpub.service.*;
import com.skillbox.devpub.service.impl.InitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
public class ApiGeneralController {

    private final InitService initService;
    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;
    private final FileService fileService;

    @Autowired
    public ApiGeneralController(InitServiceImpl initService, PostService postService, CommentService commentService, TagService tagService, FileService fileService) {
        this.initService = initService;
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
        this.fileService = fileService;
    }

    @GetMapping("/init")
    public ResponseEntity<?> init() {
        return ResponseEntity.ok(initService.init());
    }

    @PostMapping("/moderation")
    @PreAuthorize("hasAnyAuthority('user:moderate')")
    public void postModeration(@RequestBody PostModerationRequestDto request, Principal principal) {
        postService.postModeration(request, principal);
//        return ResponseEntity.ok();
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> postComment(@RequestBody CommentRequestDto request, Principal principal) {
        return ResponseEntity.ok(commentService.postComment(request, principal));
    }

    @GetMapping("/tag")
    public ResponseEntity<?> getTagsWeight(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(tagService.getTagsWeight(query));
    }

    @PostMapping("/image")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public String saveFile(@RequestParam("image") MultipartFile fileRequest/*@ModelAttribute FileRequestDto fileRequest*/) throws IOException {
//        if (fileRequest.isEmpty()) {
//            System.out.println("ошибка");
//        }
//        System.out.println(fileRequest.getOriginalFilename());
        return fileService.saveFile(fileRequest);
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendar(@RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(postService.getCalendar(year));
    }
}

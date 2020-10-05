package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.vote.VoteRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.service.AuthService;
import com.skillbox.devpub.service.PostService;
import com.skillbox.devpub.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/post")
public class ApiPostController {
    private final PostService postService;
    private final AuthService authService;
    private final VoteService voteService;

    @Autowired
    public ApiPostController(PostService postService, AuthService authService, VoteService voteService) {
        this.postService = postService;
        this.authService = authService;
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                      @RequestParam(required = false, defaultValue = "20") Integer limit,
                                      @RequestParam(required = false) String mode) {
        return ResponseEntity.ok(postService.getPosts(offset, limit, mode));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> addPost(@RequestBody PostRequestDto requestDto, Principal principal
                                     /*HttpServletRequest servletRequest*/) {
//        System.out.println(requestDto);
//        User user = authService.getAuthUser(servletRequest);
//        if (user == null) {
////            System.out.println(user);
//            return ResponseEntity.ok(new BaseResponse(false));
//        }
        return ResponseEntity.ok(postService.addPost(requestDto, principal));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> editPost(@PathVariable Integer id,
                                      @RequestBody PostRequestDto requestDto, Principal principal
                                      /*HttpServletRequest servletRequest*/) {
//        User user = authService.getAuthUser(servletRequest);
//        if (user == null) {
//            return ResponseEntity.ok(new BaseResponse(false));
//        }
        return ResponseEntity.ok(postService.editPost(requestDto, id, principal));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id) {
//        Response result = postService.getPost(id);
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping(value = "/like")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> postLike(@RequestBody VoteRequestDto request, Principal principal) {
        return ResponseEntity.ok(voteService.postLike(request, principal));
    }

    @PostMapping(value = "/dislike")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> postDislike(@RequestBody VoteRequestDto request, Principal principal) {
        return ResponseEntity.ok(voteService.postDislike(request, principal));
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAnyAuthority('user:moderate')")
    public ResponseEntity<?> moderationList(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                            @RequestParam(required = false, defaultValue = "20") Integer limit,
                                            @RequestParam(required = false) String status, Principal principal) {
        return ResponseEntity.ok(postService.getModerationList(offset, limit, status, principal));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> getMyPosts(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                        @RequestParam(required = false, defaultValue = "20") Integer limit,
                                        @RequestParam(required = false) String status, Principal principal) {
        return ResponseEntity.ok(postService.getMyPosts(offset, limit, status, principal));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                         @RequestParam(required = false, defaultValue = "20") Integer limit,
                                         @RequestParam(required = false) String query) {
        return ResponseEntity.ok(postService.searchPosts(offset, limit, query));
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> getPostsByDate(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                            @RequestParam(required = false, defaultValue = "20") Integer limit,
                                            @RequestParam(required = false) String date) {
//        System.out.println(date);
        return ResponseEntity.ok(postService.getPostsByDate(offset, limit, date));
    }

    @GetMapping("/byTag")
    public ResponseEntity<?> getPostsByTag(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                            @RequestParam(required = false, defaultValue = "20") Integer limit,
                                            @RequestParam(required = false) String tag) {
//        System.out.println(date);
        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }
}

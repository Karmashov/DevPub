package com.skillbox.devpub.controller;

import com.skillbox.devpub.dto.comment.CommentRequestDto;
import com.skillbox.devpub.dto.post.PostModerationRequestDto;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.dto.universal.SettingsDto;
import com.skillbox.devpub.dto.user.ProfileEditRequestDto;
import com.skillbox.devpub.repository.GlobalSettingsRepository;
import com.skillbox.devpub.service.*;
import com.skillbox.devpub.service.impl.InitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
public class ApiGeneralController {

    private final InitService initService;
    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;
    private final FileService fileService;
    private final SettingsService settingsService;
    private final UserService userService;
    private final GlobalSettingsRepository settingsRepository;

    @Autowired
    public ApiGeneralController(InitServiceImpl initService,
                                PostService postService,
                                CommentService commentService,
                                TagService tagService,
                                FileService fileService,
                                SettingsService settingsService,
                                UserService userService,
                                GlobalSettingsRepository settingsRepository) {
        this.initService = initService;
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
        this.fileService = fileService;
        this.settingsService = settingsService;
        this.userService = userService;
        this.settingsRepository = settingsRepository;
    }

    @GetMapping("/init")
    public ResponseEntity<?> init() {
        return ResponseEntity.ok(initService.init());
    }

    @PostMapping("/moderation")
    @PreAuthorize("hasAnyAuthority('user:moderate')")
    public void postModeration(@RequestBody PostModerationRequestDto request,
                               Principal principal) {
        postService.postModeration(request, principal);
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> postComment(@RequestBody CommentRequestDto request,
                                         Principal principal) {
        return ResponseEntity.ok(commentService.postComment(request, principal));
    }

    @GetMapping("/tag")
    public ResponseEntity<?> getTagsWeight(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(tagService.getTagsWeight(query));
    }

    @PostMapping("/image")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public String saveFile(@RequestParam("image") MultipartFile fileRequest) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(fileRequest.getInputStream());
        String fileFormat = fileRequest.getOriginalFilename().substring(fileRequest.getOriginalFilename().lastIndexOf(".") + 1);
        return fileService.saveFile(bufferedImage, fileFormat);
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> getCalendar(@RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(postService.getCalendar(year));
    }

    @GetMapping("/statistics/my")
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> getMyStatistics(Principal principal) {
        return ResponseEntity.ok(postService.getMyStatistics(principal));
    }

    @GetMapping("/statistics/all")
    public ResponseEntity<?> getAllStatistics(Principal principal) {
        if (settingsRepository.findByCode("STATISTICS_IS_PUBLIC").getValue().equals("NO") &&
                (principal == null ||
                        !userService.findByEmail(principal.getName()).getIsModerator())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(postService.getAllStatistics(principal));
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PutMapping("/settings")
    @PreAuthorize("hasAnyAuthority('user:moderate')")
    public void editSettings(@RequestBody SettingsDto request) {
        settingsService.editSettings(request);
    }

    @RequestMapping(path = "/profile/my", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> editProfileWithPhoto(@ModelAttribute /*@RequestBody*/ ProfileEditRequestDto request,
                                                  Principal principal) {
        return ResponseEntity.ok(userService.editProfile(request, principal));
    }

    @RequestMapping(path = "/profile/my", method = RequestMethod.POST,
            consumes = {"application/json"})
    @PreAuthorize("hasAnyAuthority('user:write')")
    public ResponseEntity<?> editProfile(@RequestBody ProfileEditRequestDto request,
                                         Principal principal) {
        return ResponseEntity.ok(userService.editProfile(request, principal));
    }
}

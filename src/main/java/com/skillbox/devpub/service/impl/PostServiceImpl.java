package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.post.PostModerationRequestDto;
import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.post.PostResponseFactory;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.BaseResponseList;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.universal.ResponseFactory;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.repository.TagRepository;
import com.skillbox.devpub.service.PostService;
import com.skillbox.devpub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final UserService userService;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    public PostServiceImpl(UserService userService, TagRepository tagRepository, PostRepository postRepository) {
        this.userService = userService;
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Response getPosts(Integer offset, Integer limit, String mode) {
//        List<Post> result = postRepository.findAll().stream().filter(Post::getIsActive)
//                .filter(p -> p.getModerationStatus() == ModerationStatus.ACCEPTED)
//                .filter(p -> p.getTime().isBefore(LocalDateTime.now()))/*.map(p -> PostResponseFactory.postToDto(p))*/.collect(Collectors.toList());
        List<Post> result = postRepository.findAllByIsActiveAndModerationStatusAndTimeBefore(
                true,
                ModerationStatus.ACCEPTED,
                LocalDateTime.now()
        );
        switch (mode) {
            case "recent":
                result.sort(Post.COMPARE_BY_TIME);
                break;
            case "early":
                result.sort(Collections.reverseOrder(Post.COMPARE_BY_TIME));
                break;
            case "popular":
                result.sort(Post.COMPARE_BY_COMMENTS);
                break;
            case "best":            //@TODO влияют ли дизлайки?
                result.sort(Post.COMPARE_BY_VOTES);
                break;
        }
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public Response addPost(PostRequestDto requestDto, Principal principal) {
        Response errors = checkErrors(requestDto, principal);
        if (errors != null) {
            return errors;
        }

        Post post = new Post();
        post.setIsActive(requestDto.getActive());
        post.setModerationStatus(ModerationStatus.NEW);
        post.setUser(userService.findByEmail(principal.getName()));
        post.setTime(parseDate(requestDto.getTime()));
        post.setTitle(requestDto.getTitle());
        post.setText(requestDto.getText());
        post.setVotes(new ArrayList<>());
        post.setComments(new ArrayList<>());
        post.setViewCount(0);

        Set<Tag> tags = new HashSet<>();
        if (requestDto.getTags().size() != 0) {
            post.setTags(setPostTag(tags, requestDto));
        }
        postRepository.save(post);

        log.info("IN addPost post: {} added with tags: {} successfully", post.getId(), tags);

        return ResponseFactory.responseOk();
    }

    //@TODO на фронте при открытии поста, все пустое
    @Override
    public Response editPost(PostRequestDto requestDto, Integer postId, Principal principal) {
        Response errors = checkErrors(requestDto, principal);
        if (errors != null) {
            return errors;
        }

        Post post = postRepository.findPostById(postId);
        post.setTime(parseDate(requestDto.getTime()));
        post.setIsActive(requestDto.getActive());
        post.setTitle(requestDto.getTitle());
        post.setText(requestDto.getText());

        Set<Tag> tags = new HashSet<>();
        if (requestDto.getTags().size() != 0) {
            post.setTags(setPostTag(tags, requestDto));
        }

        //@TODO Изменение модератором
        if (!userService.findByEmail(principal.getName()).getIsModerator()) {
            post.setModerationStatus(ModerationStatus.NEW);
        }

        postRepository.save(post);

        log.info("IN editPost post: {} edited with tags: {} successfully", post.getId(), tags);

        return ResponseFactory.responseOk();
    }

    @Override
    public Post findById(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            log.warn("IN findById - no post found by ID: {}", id);
            return null;
        }
        log.info("IN findById - post: {} found by ID: {}", post, id);

        return post;
    }

    @Override
    public Response getPost(Integer id) {
        Post post = postRepository.findPostById(id);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
//        System.out.println(PostResponseFactory.getSinglePost(post));
        if (post.getIsActive() &&
                post.getModerationStatus() == ModerationStatus.ACCEPTED &&
                post.getTime().isBefore(LocalDateTime.now())) {
            return PostResponseFactory.getSinglePost(post);
        }
        return null;
    }

    //@TODO включать или не включать отложенные посты?
    @Override
    public Response getModerationList(Integer offset, Integer limit, String status, Principal principal) {
        ModerationStatus moderationStatus = ModerationStatus.valueOf(status.toUpperCase());
        List<Post> result;
        if (moderationStatus.equals(ModerationStatus.NEW)) {
            result = postRepository.findAllByIsActiveAndModerationStatus(
                    true,
                    moderationStatus
            );
        } else {
            result = postRepository
                    .findAllByIsActiveAndModerationStatusAndModerator(
                            true,
                            moderationStatus,
                            userService.findByEmail(principal.getName()));
        }
//        System.out.println(moderationStatus);
//        switch (status) {
//            case "new":
//                result.stream().filter(p -> p.getModerationStatus() == ModerationStatus.NEW).collect(Collectors.toList());
//                break;
//            case "declined":
//                result.stream().filter(p -> p.getModerationStatus() == ModerationStatus.DECLINED);
//                break;
//            case "accepted":
//                result.stream().filter(p -> p.getModerationStatus() == ModerationStatus.ACCEPTED);
//                break;
//        }
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public void postModeration(PostModerationRequestDto request, Principal principal) {
        Post post = postRepository.findPostById(request.getPost());
        if (request.getDecision().equals("accept")) {
            post.setModerationStatus(ModerationStatus.ACCEPTED);
        } else if (request.getDecision().equals("decline")) {
            post.setModerationStatus(ModerationStatus.DECLINED);
        }
        post.setModerator(userService.findByEmail(principal.getName()));
        postRepository.save(post);
    }

    @Override
    public Response getMyPosts(Integer offset, Integer limit, String status, Principal principal) {
//        ModerationStatus moderationStatus = ModerationStatus.valueOf(status.toUpperCase());
        User user = userService.findByEmail(principal.getName());
        List<Post> result = new ArrayList<>();
        switch (status) {
            case "inactive":
                result = postRepository.findAllByUserAndIsActive(user, false);
                break;
            case "pending":
                result = postRepository.findAllByUserAndIsActiveAndModerationStatus(user, true, ModerationStatus.NEW);
                break;
            case "declined":
                result = postRepository.findAllByUserAndIsActiveAndModerationStatus(user, true, ModerationStatus.DECLINED);
                break;
            case "published":
                result = postRepository.findAllByUserAndIsActiveAndModerationStatus(user, true, ModerationStatus.ACCEPTED);
                break;
        }
//        System.out.println(result);
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public Response searchPosts(Integer offset, Integer limit, String query) {
        List<Post> result = postRepository.findAllByIsActiveAndModerationStatus(true, ModerationStatus.ACCEPTED)
                .stream().filter(f -> f.getText().contains(query)).collect(Collectors.toList());
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public Response getPostsByDate(Integer offset, Integer limit, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate queryDate = LocalDate.parse(date, formatter);
        List<Post> result = postRepository.findAllByIsActiveAndModerationStatus(true, ModerationStatus.ACCEPTED)
                .stream()
                .filter(f -> f.getTime().toLocalDate().equals(queryDate))
                .collect(Collectors.toList());
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public Response getPostsByTag(Integer offset, Integer limit, String tag) {
        List<Post> result = postRepository
                .findAllByIsActiveAndModerationStatusAndTagsAndTimeBefore(true,
                                                                ModerationStatus.ACCEPTED,
                                                                tagRepository.findFirstTagByNameIgnoreCase(tag),
                                                                LocalDateTime.now());
        return PostResponseFactory.getPostsListWithLimit(result, offset, limit);
    }

    @Override
    public Response getCalendar(Integer year) {
        if (year == null) {
            year = LocalDateTime.now().getYear();
        }
        List<Post> search = postRepository
                .findAllByIsActiveAndModerationStatusAndTimeBefore(true, ModerationStatus.ACCEPTED, LocalDateTime.now());
        Integer finalYear1 = year;
        search.stream().filter(f -> f.getTime().getYear() == finalYear1).collect(Collectors.groupingBy(Post::getTime, Collectors.toList()));

        System.out.println(search.stream()
                .filter(f -> f.getTime().getYear() == finalYear1)
                .collect(Collectors.groupingBy(Post::getTime, Collectors.counting())));
        return null;
    }

    private Response checkErrors(PostRequestDto requestDto, Principal principal) {
        if (principal == null) {
            return new BaseResponse(false);
        }

        HashMap<String, String> errors = new HashMap<>();
        checkTitle(requestDto.getTitle(), errors);
        checkText(requestDto.getText(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }
        return null;
    }

    private Set<Tag> setPostTag(Set<Tag> tags, PostRequestDto requestDto) {
        requestDto.getTags().forEach(tag -> {
            Tag postTag;
            if (tagRepository.existsTagByNameIgnoreCase(tag)) {
                postTag = tagRepository.findFirstTagByNameIgnoreCase(tag);
            } else {
                postTag = new Tag();
                postTag.setName(tag);
            }
            tags.add(postTag);
        });
        return tags;
    }

    private void checkTitle(String title, HashMap<String, String> errors) {
        if (title.length() < 3) {
            errors.put("title", "Заголовок не установлен");
        }
    }

    private void checkText(String text, HashMap<String, String> errors) {
        if (text.length() < 50) {
            errors.put("text", "Текст публикации слишком короткий");
        }
    }

    private LocalDateTime parseDate(String requestDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(requestDate, formatter);
        LocalDateTime now = LocalDateTime.now();
        if (requestDate.isEmpty() || localDateTime.isBefore(now)) {
            return now;
        }
        return localDateTime;
    }
}

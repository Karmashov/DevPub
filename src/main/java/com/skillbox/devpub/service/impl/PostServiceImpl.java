package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.post.PostRequestDto;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    //@TODO получение постов
    @Override
    public List getPosts() {
        return null;
    }

    @Override
    public Response addPost(PostRequestDto requestDto, User user) {
        HashMap<String, String> errors = new HashMap<>();
        checkTitle(requestDto.getTitle(), errors);
        checkText(requestDto.getText(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        Post post = new Post();
        post.setIsActive(requestDto.getActive());
        post.setModerationStatus(ModerationStatus.NEW);
        post.setUser(user);
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

    @Override
    public Response editPost(PostRequestDto requestDto, Integer postId, User user) {
        HashMap<String, String> errors = new HashMap<>();
        checkTitle(requestDto.getTitle(), errors);
        checkText(requestDto.getText(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
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
        if (!user.getIsModerator()) {
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

package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.post.AddPostRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
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
    public Response addPost(AddPostRequestDto requestDto, Integer userId) {
        HashMap<String, String> errors = new HashMap<>();
        checkTitle(requestDto.getTitle(), errors);
        checkText(requestDto.getText(), errors);

        if (!errors.isEmpty()) {
            return ResponseFactory.getErrorListResponse(errors);
        }

        User user = userService.findById(userId);
        Post post = new Post();
        post.setIsActive(requestDto.getActive());
        post.setModerationStatus(ModerationStatus.NEW);
        post.setUser(user);
//        post.setTime(requestDto.getTime());
        post.setTime(parseDate(requestDto.getTime()));
        post.setTitle(requestDto.getTitle());
        post.setText(requestDto.getText());
        post.setVotes(new ArrayList<>());
        post.setComments(new ArrayList<>());
        post.setViewCount(0);
//        post.setTags(requestDto.getTags().add);
        Set<Tag> tags = new HashSet<>();
        if (requestDto.getTags().size() != 0) {
            requestDto.getTags().forEach(tag -> {
                Tag postTag;
                //@TODO проверка существования тэгов в базе
//                if (tagRepository.existsByTagIgnoreCase(tag)) {
//                    postTag = tagRepository.findFirstByTagIgnoreCase(tag);
//                } else {
                    postTag = new Tag();
                    postTag.setName(tag);
//                }
                tags.add(postTag);
            });
            post.setTags(tags);
        }
        Post result = postRepository.save(post);

        log.info("IN addPost post: {} added with tags: {} successfully", post.getId(), tags);

        return ResponseFactory.responseOk();
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
        } else {
            return localDateTime;
        }
//        return localDateTime;
    }
}

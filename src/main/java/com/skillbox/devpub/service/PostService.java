package com.skillbox.devpub.service;

import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    List getPosts();

    Post addPost(LocalDateTime time, Boolean active, String title, List<Tag> tags, String text);
}

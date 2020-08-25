package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    //@TODO получение постов
    @Override
    public List getPosts() {
        return null;
    }

    @Override
    public Post addPost(LocalDateTime time, Boolean active, String title, List<Tag> tags, String text) {

        return null;
    }
}

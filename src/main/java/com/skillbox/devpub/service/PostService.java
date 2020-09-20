package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.post.PostResponseDto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.User;

import java.util.List;

public interface PostService {

    List getPosts();

    Response addPost(PostRequestDto requestDto, User user);

    Response editPost(PostRequestDto requestDto, Integer postId, User user);

    Post findById(Integer id);

    //@TODO разобраться с респонсами (ResponseOk)
    PostResponseDto getPost(Integer id);
}

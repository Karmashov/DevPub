package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.post.AddPostRequestDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    List getPosts();

    Response addPost(AddPostRequestDto requestDto, Integer id);
}

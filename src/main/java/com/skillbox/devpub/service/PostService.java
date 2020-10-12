package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.post.PostModerationRequestDto;
import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.post.PostResponseDto;
import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.BaseResponseList;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.User;

import java.security.Principal;
import java.util.List;

public interface PostService {

    Response getPosts(Integer offset, Integer limit, String mode);

    Response addPost(PostRequestDto requestDto, Principal principal);

    Response editPost(PostRequestDto requestDto, Integer postId, Principal principal);

    Post findById(Integer id);

    Response getPost(Integer id);

    Response getModerationList(Integer offset, Integer limit, String status, Principal principal);

    void postModeration(PostModerationRequestDto request, Principal principal);

    Response getMyPosts(Integer offset, Integer limit, String status, Principal principal);

    Response searchPosts(Integer offset, Integer limit, String query);

    Response getPostsByDate(Integer offset, Integer limit, String date);

    Response getPostsByTag(Integer offset, Integer limit, String tag);

    Response getCalendar(Integer year);

    Response getMyStatistics(Principal principal);

    Response getAllStatistics(Principal principal);
}

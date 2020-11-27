package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.post.PostModerationRequestDto;
import com.skillbox.devpub.dto.post.PostRequestDto;
import com.skillbox.devpub.dto.universal.Response;

import java.security.Principal;

public interface PostService {

    Response getPosts(Integer offset, Integer limit, String mode);

    Response addPost(PostRequestDto requestDto, Principal principal);

    Response editPost(PostRequestDto requestDto, Integer postId, Principal principal);

    Response getPost(Integer id, Principal principal);

    Response getModerationList(Integer offset, Integer limit, String status, Principal principal);

    void postModeration(PostModerationRequestDto request, Principal principal);

    Response getMyPosts(Integer offset, Integer limit, String status, Principal principal);

    Response searchPosts(Integer offset, Integer limit, String query);

    Response getPostsByDate(Integer offset, Integer limit, String date);

    Response getPostsByTag(Integer offset, Integer limit, String tag);

    Response getCalendar(Integer year);

    Response getMyStatistics(Principal principal);

    Response getAllStatistics();
}

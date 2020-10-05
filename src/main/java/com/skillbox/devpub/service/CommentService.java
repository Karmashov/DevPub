package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.comment.CommentRequestDto;
import com.skillbox.devpub.dto.universal.Response;

import java.security.Principal;

public interface CommentService {

    Response postComment(CommentRequestDto request, Principal principal);
}

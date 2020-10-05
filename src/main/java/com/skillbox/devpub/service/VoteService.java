package com.skillbox.devpub.service;

import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.vote.VoteRequestDto;

import java.security.Principal;

public interface VoteService {

    Response postLike(VoteRequestDto request, Principal principal);

    Response postDislike(VoteRequestDto request, Principal principal);
}

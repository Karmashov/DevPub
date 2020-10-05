package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.universal.BaseResponse;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.dto.vote.VoteRequestDto;
import com.skillbox.devpub.model.PostVote;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.repository.PostVoteRepository;
import com.skillbox.devpub.service.UserService;
import com.skillbox.devpub.service.VoteService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostVoteRepository voteRepository;

    public VoteServiceImpl(UserService userService, PostRepository postRepository, PostVoteRepository voteRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public Response postLike(VoteRequestDto request, Principal principal) {
        if (principal == null) {
            return new BaseResponse(false);
        }
        List<PostVote> voteList = voteRepository.findAllByPost_Id(request.getPost());
        for (PostVote vote : voteList) {
            if (vote.getUser().getEmail().equals(principal.getName())) {
                if (vote.getValue() == -1) {
                    vote.setValue(1);
                    voteRepository.save(vote);
                    return new BaseResponse(true);
                }
                return new BaseResponse(false);
            }
        }
        PostVote vote = new PostVote();
        vote.setTime(LocalDateTime.now());
        vote.setValue(1);
        vote.setPost(postRepository.findPostById(request.getPost()));
        vote.setUser(userService.findByEmail(principal.getName()));
        voteRepository.save(vote);
        return new BaseResponse(true);
    }

    @Override
    public Response postDislike(VoteRequestDto request, Principal principal) {
        if (principal == null) {
            return new BaseResponse(false);
        }
        List<PostVote> voteList = voteRepository.findAllByPost_Id(request.getPost());
        for (PostVote vote : voteList) {
            if (vote.getUser().getEmail().equals(principal.getName())) {
                if (vote.getValue() == 1) {
                    vote.setValue(-1);
                    voteRepository.save(vote);
                    return new BaseResponse(true);
                }
                return new BaseResponse(false);
            }
        }
        PostVote vote = new PostVote();
        vote.setTime(LocalDateTime.now());
        vote.setValue(-1);
        vote.setPost(postRepository.findPostById(request.getPost()));
        vote.setUser(userService.findByEmail(principal.getName()));
        voteRepository.save(vote);
        return new BaseResponse(true);
    }
}

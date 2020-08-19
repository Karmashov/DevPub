package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote,Integer> {
}

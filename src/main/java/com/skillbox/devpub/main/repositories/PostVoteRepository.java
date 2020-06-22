package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote,Integer> {
}

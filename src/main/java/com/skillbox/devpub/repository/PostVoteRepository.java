package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

    boolean existsPostVoteByPost_Id(Integer id);

    List<PostVote> findAllByPost_Id(Integer id);
}

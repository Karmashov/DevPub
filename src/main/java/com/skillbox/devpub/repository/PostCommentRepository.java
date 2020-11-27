package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    Optional<PostComment> findPostCommentById(Integer id);
}

package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    PostComment findPostCommentById(Integer id);
}

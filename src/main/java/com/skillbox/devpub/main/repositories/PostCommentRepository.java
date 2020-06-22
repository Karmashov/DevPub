package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment,Integer> {
}

package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Post findPostById(Integer id);

    List<Post> searchPosts();
}

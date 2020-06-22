package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}

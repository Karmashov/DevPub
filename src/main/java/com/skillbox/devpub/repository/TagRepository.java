package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}

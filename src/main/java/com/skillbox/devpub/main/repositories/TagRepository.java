package com.skillbox.devpub.main.repositories;

import com.skillbox.devpub.main.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}

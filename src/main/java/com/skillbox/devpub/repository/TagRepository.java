package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    boolean existsTagByNameIgnoreCase(String name);

    Tag findFirstTagByNameIgnoreCase(String name);

    List<Tag> findAllByNameContains(String query);
}

package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Integer> {

//    Tag findFirstByTagIgnoreCase(String name);
//
//    boolean existsByTagIgnoreCase(String name);

    boolean existsTagByNameIgnoreCase(String name);

    Tag findFirstTagByNameIgnoreCase(String name);

    List<Tag> findAllByNameContains(String query);

//    List<Tag> fi

//    List<Tag> findAllByNameContainsWh(String query)
}

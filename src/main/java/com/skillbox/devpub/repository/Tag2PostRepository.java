package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.Tag2Post;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface Tag2PostRepository extends JpaRepository<Tag2Post,Integer> {

    List<Tag2Post> findAllByTagAndPostIsActiveAndPostModerationStatusAndPostTimeBefore(Tag tag, Boolean isActive, ModerationStatus status, LocalDateTime time);

}

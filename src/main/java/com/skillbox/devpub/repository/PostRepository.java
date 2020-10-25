package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findPostById(Integer id);

    List<Post> findAllByIsActiveAndModerationStatusAndTimeBefore(Boolean isActive, ModerationStatus status, LocalDateTime time);

    List<Post> findAllByIsActiveAndModerationStatus(Boolean isActive, ModerationStatus status);

    List<Post> findAllByIsActiveAndModerationStatusAndModeratorAndTimeBefore(Boolean isActive, ModerationStatus status, User moderator, LocalDateTime time);

    List<Post> findAllByUserAndIsActive(User user, Boolean isActive);

    List<Post> findAllByUserAndIsActiveAndModerationStatus(User user, Boolean isActive, ModerationStatus status);

    List<Post> findAllByTags(String tag);

    List<Post> findAllByIsActiveAndModerationStatusAndTagsAndTimeBefore(Boolean isActive, ModerationStatus status, Tag tag, LocalDateTime time);
}

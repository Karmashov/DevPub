package com.skillbox.devpub.repository;

import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.User;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

//    Post findPostById(Integer id);

    Optional<Post> findByIdAndIsActiveAndModerationStatusAndTimeBefore(Integer id, Boolean isActive, ModerationStatus status, LocalDateTime time);

    List<Post> findAllByIsActiveAndModerationStatusAndTimeBefore(Boolean isActive, ModerationStatus status, LocalDateTime time);

    List<Post> findAllByIsActiveAndModerationStatus(Boolean isActive, ModerationStatus status);

    List<Post> findAllByIsActiveAndModerationStatusAndModeratorAndTimeBefore(Boolean isActive, ModerationStatus status, User moderator, LocalDateTime time);

    List<Post> findAllByUserAndIsActive(User user, Boolean isActive);

    List<Post> findAllByUserAndIsActiveAndModerationStatus(User user, Boolean isActive, ModerationStatus status);

    List<Post> findAllByTags(String tag);

    List<Post> findAllByIsActiveAndModerationStatusAndTagsAndTimeBefore(Boolean isActive, ModerationStatus status, Tag tag, LocalDateTime time);

    //SELECT dev_pub.posts.*, COUNT(dev_pub.post_comments.id) AS total
    //FROM dev_pub.posts LEFT JOIN dev_pub.post_comments ON dev_pub.posts.id = dev_pub.post_comments.post_id
    //WHERE dev_pub.posts.is_active = '1' AND dev_pub.posts.moderation_status = 'ACCEPTED' AND dev_pub.posts.time < '2020-01-01'
    //GROUP BY dev_pub.posts.id ORDER BY total DESC;
    @Query(value = "SELECT p, COUNT(c.id) AS total FROM Post p " +
            "LEFT JOIN p.comments c ON p.id = c.post.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time < ?1 " +
            "GROUP BY p.id ORDER BY total DESC")
    List<Post> sortByComments(LocalDateTime time);

    @Query(value = "SELECT p FROM Post p " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time < ?1 " +
            "ORDER BY p.time DESC")
    List<Post> sortByDateFromLast(LocalDateTime time);

    @Query(value = "SELECT p FROM Post p " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time < ?1 " +
            "ORDER BY p.time")
    List<Post> sortByDateFromFirst(LocalDateTime time);

    //Черный Пиар - тоже Пиар=)
    @Query(value = "SELECT p, COUNT(v.value) AS total FROM Post p " +
            "LEFT JOIN p.votes v ON p.id = v.post.id " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time < ?1 AND v.value > 0" +
            "GROUP BY p.id ORDER BY total DESC")
    List<Post> sortByVotes(LocalDateTime time);
}

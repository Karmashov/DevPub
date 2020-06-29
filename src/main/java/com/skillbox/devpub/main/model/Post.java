package com.skillbox.devpub.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "tinyint")
    private Boolean isActive;

    @Enumerated(value = EnumType.STRING)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;

    private String title;

    @Type(type = "text")
    private String text;

    private Integer viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostVote> votes;

    @OneToMany(mappedBy = "post")
    private List<Tag2Post> tags;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;
}

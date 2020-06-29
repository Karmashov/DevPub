package com.skillbox.devpub.main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "post_votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime time;

    @Column(columnDefinition = "tinyint")
    //@TODO решить вопрос с предопределенностью лайков/дизлайков
    private Integer value;
}

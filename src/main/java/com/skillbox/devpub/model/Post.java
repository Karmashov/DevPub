package com.skillbox.devpub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private ModerationStatus moderationStatus/* = ModerationStatus.NEW*/;

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

//    @OneToMany(mappedBy = "post")
//    private Set<Tag2Post> tags;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "tag2posts",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @Override
    public String toString() {
        return id + " " + title + " " + text;
    }
}

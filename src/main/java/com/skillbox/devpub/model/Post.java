package com.skillbox.devpub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private ModerationStatus moderationStatus;

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

    @JsonIgnore
    public static final Comparator<? super Post> COMPARE_BY_TIME = (Comparator<Post>) (p1, p2) -> {
        if (p1.getTime() == p2.getTime()) return 0;
        else if (p1.getTime().isAfter(p2.getTime())) return -1;
        else return 1;
    };

    @JsonIgnore
    public static final Comparator<? super Post> COMPARE_BY_COMMENTS = (Comparator<Post>) (p1, p2) -> {
        if (p1.getComments().size() == p2.getComments().size()) return 0;
        else if (p1.getComments().size() > p2.getComments().size()) return -1;
        else return 1;
    };

    @JsonIgnore
    //@TODO разобраться с тем, что 0-0 не на своем месте
    public static final Comparator<? super Post> COMPARE_BY_VOTES = (Comparator<Post>) (p1, p2) -> {
        int v1 = (int) p1.getVotes().stream().filter(v -> v.getValue() > 0)
                .map(PostVote::getValue).count();
        int v2 = (int) p2.getVotes().stream().filter(v -> v.getValue() > 0)
                .map(PostVote::getValue).count();
        if (v1 == v2) return 0;
        else if (v1 > v2) return -1;
        else return 1;
    };
}

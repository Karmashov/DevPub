package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(columnDefinition = "tinyint")
    private Boolean isActive;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    //@TODO Можно ли инициализировать Entity?
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    //@TODO Дата?
    private Date time;

    @NotNull
    private String title;

    @NotNull
    @Type(type = "text")
    private String text;

    @NotNull
    private Integer viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostVote> votes;

    @OneToMany(mappedBy = "post")
    private List<Tag2Post> tags;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;
}

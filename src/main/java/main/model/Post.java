package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private Boolean isActive;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    //@TODO Можно ли инициализировать Entity?
    private ModerationStatus moderationStatus = ModerationStatus.NEW;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    //@TODO Настроить связь
    private User moderator;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@TODO Настроить связь
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
}

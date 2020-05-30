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
@Table(name = "post_comments")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComment parentComment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    //@TODO Настроить связь
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@TODO Настроить связь
    private User user;

    @NotNull
    //@TODO Дата?
    private Date time;

    @NotNull
    @Type(type = "text")
    private String text;
}

package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "post_votes")
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@TODO Настроить связь
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    //@TODO Настроить связь
    private Post post;

    @NotNull
    //@TODO Дата?
    private Date time;

//    @NotNull
//    private Boolean
}

package com.skillbox.devpub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "tinyint")
    private Boolean isModerator;

    private LocalDateTime regTime;

    private String name;

    private String email;

    private String password;

    private String code;

    @Type(type = "text")
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public com.skillbox.devpub.model.enumerated.Role getRole() {
        return isModerator ? com.skillbox.devpub.model.enumerated.Role.MODERATOR : com.skillbox.devpub.model.enumerated.Role.USER;
    }

    @OneToMany(mappedBy = "moderator")
    private List<Post> moderatedPosts;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<PostVote> votes;

    @OneToMany(mappedBy = "user")
    private List<PostComment> comments;

    @Override
    public String toString() {
        return id + " " + name;
    }
}

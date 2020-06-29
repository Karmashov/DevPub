package com.skillbox.devpub.main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag2posts")
public class Tag2Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}

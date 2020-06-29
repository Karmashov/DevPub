package com.skillbox.devpub.main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "global_settings")
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@TODO code
//    @Enumerated(value = EnumType.STRING)
    private String code;

    //@TODO name
    private String name;

    //@TODO value
    private String value;
}

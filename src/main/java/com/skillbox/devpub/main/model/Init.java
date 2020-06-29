package com.skillbox.devpub.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
@Data
//@Table(name = "init")
public class Init {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

//    @NotNull
    private String title;

//    @NotNull
    private String subtitle;

//    @NotNull
    private String phone;

//    @NotNull
    private String email;

//    @NotNull
    private String copyright;

//    @NotNull
    private String copyrightFrom;

    public Init(String title, String subtitle, String phone, String email, String copyright, String copyrightFrom) {
        this.title = title;
        this.subtitle = subtitle;
        this.phone = phone;
        this.email = email;
        this. copyright = copyright;
        this.copyrightFrom = copyrightFrom;
    }
}
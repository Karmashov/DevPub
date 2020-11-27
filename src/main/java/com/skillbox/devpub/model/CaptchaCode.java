package com.skillbox.devpub.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "captcha_codes")
public class CaptchaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime time;

    @Column(columnDefinition = "tinytext")
    private String code;

    @Column(columnDefinition = "tinytext")
    private String secretCode;
}

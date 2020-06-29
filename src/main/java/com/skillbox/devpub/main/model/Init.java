package com.skillbox.devpub.main.model;

import lombok.Data;

@Data
public class Init {
    private String title;
    private String subtitle;
    private String phone;
    private String email;
    private String copyright;
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
package com.skillbox.devpub.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {
    private String time;
    private Boolean active;
    private String title;
    private List<String> tags;
    private String text;

}

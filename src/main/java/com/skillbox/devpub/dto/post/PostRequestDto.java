package com.skillbox.devpub.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {

    @JsonProperty("timestamp")
    private Long time;
    private Boolean active;
    private String title;
    private List<String> tags;
    private String text;

}

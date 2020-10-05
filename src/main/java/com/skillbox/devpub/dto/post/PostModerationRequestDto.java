package com.skillbox.devpub.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostModerationRequestDto {
    @JsonProperty("post_id")
    private Integer post;
    private String decision;
}

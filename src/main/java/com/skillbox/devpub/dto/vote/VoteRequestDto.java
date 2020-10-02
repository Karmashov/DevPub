package com.skillbox.devpub.dto.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VoteRequestDto {

    @JsonProperty("post_id")
    private Integer post;
}

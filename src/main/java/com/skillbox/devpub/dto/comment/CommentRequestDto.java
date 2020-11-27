package com.skillbox.devpub.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequestDto{

    @JsonProperty("parent_id")
    private Integer parentComment;
    @JsonProperty("post_id")
    private Integer post;
    private String text;

}

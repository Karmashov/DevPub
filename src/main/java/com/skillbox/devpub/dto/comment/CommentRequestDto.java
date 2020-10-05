package com.skillbox.devpub.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.model.Post;
import com.skillbox.devpub.model.PostComment;
import lombok.Data;

@Data
public class CommentRequestDto{
    @JsonProperty("parent_id")
    private Integer parentComment;
    @JsonProperty("post_id")
    private Integer post;
    private String text;

}

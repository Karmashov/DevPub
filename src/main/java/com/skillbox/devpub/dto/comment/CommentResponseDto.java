package com.skillbox.devpub.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto implements Dto, Response {
//    @JsonProperty("parent_id")
//    private int parentId;
//    @JsonProperty("comment_text")
//    private String text;
//    private int id;
//    @JsonProperty("post_id")
//    private int postId;
//    private long time;
//    @JsonProperty("author")
//    private Dto author;
//    @JsonProperty("is_blocked")
//    private boolean blocked;
//
//    @JsonProperty("sub_comments")
//    private List<Dto> subComments;
//
//    @JsonProperty("like_count")
//    private int likeCount;
//
//    @JsonProperty("is_my_like")
//    private boolean isMyLike;

        private Integer id;
        private String time;
        private String text;
        private Dto user;
//        private List<Dto> childComments;
    }
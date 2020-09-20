package com.skillbox.devpub.dto.comment;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto implements Dto {
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
        private LocalDateTime time;
        private String text;
        private Dto user;
    }
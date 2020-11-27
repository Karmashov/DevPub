package com.skillbox.devpub.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.skillbox.devpub.config.TimeSerializer;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto implements Dto, Response {
    private Integer id;
    @JsonSerialize(using = TimeSerializer.class)
    private LocalDateTime timestamp;
    private Dto user;
    private String title;
    private String announce;
    private String text;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;
    private List<Dto> comments;
    private List<String> tags;
}

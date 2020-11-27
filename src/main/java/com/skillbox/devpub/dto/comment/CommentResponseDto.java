package com.skillbox.devpub.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.skillbox.devpub.config.TimeSerializer;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto implements Dto, Response {

    private Integer id;
    @JsonSerialize(using = TimeSerializer.class)
    private LocalDateTime timestamp;
    private String text;
    private Dto user;
}
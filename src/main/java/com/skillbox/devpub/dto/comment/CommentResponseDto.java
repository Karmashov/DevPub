package com.skillbox.devpub.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto implements Dto, Response {

    private Integer id;
    private String time;
    private String text;
    private Dto user;
//        private List<Dto> childComments;
}
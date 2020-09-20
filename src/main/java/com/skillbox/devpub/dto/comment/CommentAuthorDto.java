package com.skillbox.devpub.dto.comment;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentAuthorDto implements Dto {
    private Integer id;
    private String name;
    private String photo;
}

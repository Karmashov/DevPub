package com.skillbox.devpub.dto.post;

import com.skillbox.devpub.dto.universal.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostAuthorDto implements Dto {
    private Integer id;
    private String name;
}

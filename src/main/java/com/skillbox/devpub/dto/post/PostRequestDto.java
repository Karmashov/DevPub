package com.skillbox.devpub.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class PostRequestDto {
    private String time;
    private Boolean active;
    private String title;
    private List<String> tags;
    private String text;

}

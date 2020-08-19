package com.skillbox.devpub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDto implements Dto {
    protected Integer id;
    protected Boolean isModerator;
    protected LocalDateTime regTime;
    protected String name;
    protected String email;
    protected String password;
    protected String code;
    protected String photo;
    protected String token;
}

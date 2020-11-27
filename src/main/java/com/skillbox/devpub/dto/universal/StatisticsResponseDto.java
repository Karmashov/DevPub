package com.skillbox.devpub.dto.universal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.skillbox.devpub.config.TimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatisticsResponseDto implements Response {

    private Integer postsCount;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer viewsCount;
    @JsonSerialize(using = TimeSerializer.class)
    private LocalDateTime firstPublication;
}

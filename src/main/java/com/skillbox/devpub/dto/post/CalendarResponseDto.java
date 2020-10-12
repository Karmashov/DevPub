package com.skillbox.devpub.dto.post;

import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class CalendarResponseDto implements Response {
    private Set<Integer> years;
    private Map<LocalDate, Integer> posts;
}

package com.skillbox.devpub.dto.tag;

import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TagResponseDto implements Dto, Response {

    private List<Dto> tags;
}

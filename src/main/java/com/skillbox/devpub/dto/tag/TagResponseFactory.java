package com.skillbox.devpub.dto.tag;

import com.skillbox.devpub.dto.universal.Dto;

import java.util.List;

public class TagResponseFactory {

    public static TagResponseDto tagWeight(List<Dto> weight) {
        return new TagResponseDto(weight);
    }
}

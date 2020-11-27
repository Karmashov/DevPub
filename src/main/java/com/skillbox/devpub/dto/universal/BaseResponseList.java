package com.skillbox.devpub.dto.universal;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponseList implements Response{

    private Integer count;
    private List<? extends Dto> posts;

    public BaseResponseList(Integer count, List<? extends Dto> posts) {
        this.count = count;
        this.posts = posts;
    }
}

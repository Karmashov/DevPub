package com.skillbox.devpub.dto.universal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
//@AllArgsConstructor
public class BaseResponseList implements Response{
//    private String error;
//    private long timestamp;
    private Integer count;
//    private Integer offset;
//    private Integer perPage;
    private List<? extends Dto> posts;

//    public BaseResponseList(List<? extends Dto> data) {
//        error = "string";
//        timestamp = new Date().getTime();
//        this.data = data;
//    }

    public BaseResponseList(Integer count, /*Integer offset, Integer perPage,*/ List<? extends Dto> posts) {
//        error = "string";
//        timestamp = new Date().getTime();
        this.count = count;
//        this.offset = offset;
//        this.perPage = perPage;
        this.posts = posts;
    }
}

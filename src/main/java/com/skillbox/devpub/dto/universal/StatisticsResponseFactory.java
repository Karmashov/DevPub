package com.skillbox.devpub.dto.universal;

import com.skillbox.devpub.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatisticsResponseFactory {

    public static Response getStatistics(List<Post> result) {
        int likes = 0;
        int dislikes = 0;
        int views = 0;
        LocalDateTime firstPost = LocalDateTime.now();
        for (Post post : result) {
            likes = likes + (int) post.getVotes().stream().filter(f -> f.getValue() > 0).count();
            dislikes = dislikes + (int) post.getVotes().stream().filter(f -> f.getValue() < 0).count();
            views = views + post.getViewCount();
            LocalDateTime postDate = post.getTime();
            if (postDate.isBefore(firstPost)) {
                firstPost = postDate;
            }
        }
        return new StatisticsResponseDto(
                result.size(),
                likes,
                dislikes,
                views,
                firstPost.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}

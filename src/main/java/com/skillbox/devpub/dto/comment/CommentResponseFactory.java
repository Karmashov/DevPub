package com.skillbox.devpub.dto.comment;

import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.PostComment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentResponseFactory {

    //@TODO родительские комментарии и вложенные комментарии???
    public static CommentResponseDto getCommentDto(PostComment comment, List<Dto> subList/*, Person person*/) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getText(),
                UserResponseFactory.getCommentAuthor(comment.getUser())/*,
                subList*/
//                comment.getParentComment() == null ? 0 : comment.getParentComment().getId(),
//                comment.getCommentText(),
//                comment.getId(),
//                comment.getPost().getId(),
//                comment.getTime().getTime(),
//                PersonResponseFactory.getPersonDto(comment.getAuthor()),
//                comment.getIsBlocked(),
//                subList,
//                comment.getCommentLikes() != null ? comment.getCommentLikes().size() : 0,
//                comment.getCommentLikes() != null && comment.getCommentLikes().stream()
//                        .anyMatch(commentLike -> commentLike.getPerson().getEmail().equalsIgnoreCase(person.getEmail()))
        );
    }

    public static CommentResponseDto getSingleCommentDto(PostComment comment) {
        return new CommentResponseDto(
                comment.getId(),
                null, null, null
        );
    }

    public static List<Dto> getCommentList(List<PostComment> commentList, PostComment parentComment/*, User user*/)
    {
        List<Dto> list = new ArrayList<>();
        if (commentList == null) {
            return list;
        }
        for (PostComment comment : commentList) {
            list.add(CommentResponseFactory.getCommentDto(comment, null));
        }
//        for (PostComment comment : commentList) {
//            if ((parentComment == null && comment.getParentComment() == null) ||
//                    (parentComment != null && comment.getParentComment().getId().equals(parentComment.getId()))) {
////                System.out.println(comment.getText());
////                System.out.println(comment.getChildComments());
//                list.add(CommentResponseFactory.getCommentDto(comment,
//                        getCommentList(comment.getChildComments(), comment))
//                );
//            }
//        }
//        System.out.println(commentList);
        return list;
    }
}

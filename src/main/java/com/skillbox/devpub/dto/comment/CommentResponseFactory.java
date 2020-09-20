package com.skillbox.devpub.dto.comment;

import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.user.UserResponseFactory;
import com.skillbox.devpub.model.PostComment;
import com.skillbox.devpub.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentResponseFactory {
    public static CommentDto getCommentDto(PostComment comment, List<Dto> subList/*, Person person*/)
    {
        return new CommentDto(
                comment.getId(),
                comment.getTime(),
                comment.getText(),
                UserResponseFactory.getCommentAuthor(comment.getUser())
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

    public static List<Dto> getCommentList(List<PostComment> commentList, PostComment parentComment/*, User user*/)
    {
        List<Dto> list = new ArrayList<>();
        if (commentList == null) {
            return list;
        }
        for (PostComment comment : commentList) {
            if ((parentComment == null && comment.getParentComment() == null) ||
                    (parentComment != null && comment.getParentComment().getId().equals(parentComment.getId()))) {
                list.add(CommentResponseFactory.getCommentDto(comment, null
//                        getCommentList(comment.getChildComments(), comment, person))
                ));
            }
        }
        return list;
    }
}

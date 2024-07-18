package org.mylog.domain.comment.service;

import org.mylog.domain.comment.domain.Comment;
import org.mylog.domain.comment.dto.CommentRegisterDto;

import java.util.List;

public interface CommentService {

    List<Comment> findCommentsByPostId(Long postId);

    Comment saveComment(CommentRegisterDto commentRegisterDto);

    void deleteComment(Long id);
}

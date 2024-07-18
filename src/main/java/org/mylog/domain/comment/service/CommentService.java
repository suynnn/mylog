package org.mylog.domain.comment.service;

import org.mylog.domain.comment.domain.Comment;
import org.mylog.domain.comment.dto.CommentDto;
import org.mylog.domain.comment.dto.CommentRegisterDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Page<CommentDto> getCommentsByPostId(Long postId, int page, int size);

    Comment saveComment(CommentRegisterDto commentRegisterDto);

    boolean deleteComment(Long id);
}

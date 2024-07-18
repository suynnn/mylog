package org.mylog.domain.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.comment.domain.Comment;
import org.mylog.domain.comment.dto.CommentDto;
import org.mylog.domain.comment.dto.CommentRegisterDto;
import org.mylog.domain.comment.repository.CommentRepository;
import org.mylog.domain.comment.service.CommentService;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId, pageable);
        return comments.map(CommentDto::new);
    }
    @Override
    public Comment saveComment(CommentRegisterDto commentRegisterDto) {

        User user = userService.findUserByUserId(commentRegisterDto.getUserId());

        Post post = postService.findPostById(commentRegisterDto.getPostId()).orElseThrow();

        Comment comment = Comment.builder()
                .content(commentRegisterDto.getContent())
                .commentClass(commentRegisterDto.getCommentClass())
                .parentId(commentRegisterDto.getParentId())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
        return comment;
    }

    @Override
    public boolean deleteComment(Long id) {
        if (!commentRepository.existsByParentId(id)) {
            commentRepository.deleteById(id);
        } else {
            Comment comment = commentRepository.findById(id).orElseThrow();
            comment.deleteComment();
        }

        return true;
    }
}

package org.mylog.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private int commentClass;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    private Long userId;
    private Long postId;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commentClass = comment.getCommentClass();
        this.parentId = comment.getParentId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.isDeleted = comment.getIsDeleted();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
    }
}

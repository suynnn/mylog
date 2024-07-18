package org.mylog.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentRegisterDto {

    private String content;
    private int commentClass;
    private Long parentId;
    private Long userId;
    private Long postId;
}

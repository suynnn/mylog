package org.mylog.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mylog.domain.like.domain.Like;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeDto {
    private Long id;
    private Long userId;
    private Long postId;

    public LikeDto(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
    }
}

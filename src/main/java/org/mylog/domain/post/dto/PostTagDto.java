package org.mylog.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.post.domain.PostTag;

@Getter
@Setter
@NoArgsConstructor
public class PostTagDto {
    private Long id;
    private Long postId;
    private Long tagId;

    public PostTagDto(PostTag postTag) {
        this.id = postTag.getId();
        this.postId = postTag.getPost().getId();
        this.tagId = postTag.getTag().getId();
    }
}

package org.mylog.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.post.domain.PostTag;
import org.mylog.domain.tag.dto.TagDto;

@Getter
@Setter
@NoArgsConstructor
public class PostTagDto {
    private Long id;
    private Long postId;
    private TagDto tag;

    public PostTagDto(PostTag postTag) {
        this.id = postTag.getId();
        this.postId = postTag.getPost().getId();
        this.tag = new TagDto(postTag.getTag());
    }
}

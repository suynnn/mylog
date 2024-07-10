package org.mylog.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.post.domain.PostImage;

@Getter
@Setter
@NoArgsConstructor
public class PostImageDto {
    private Long id;
    private String imageUrl;
    private Long postId;

    public PostImageDto(PostImage postImage) {
        this.id = postImage.getId();
        this.imageUrl = postImage.getImageUrl();
        this.postId = postImage.getPost().getId();
    }
}

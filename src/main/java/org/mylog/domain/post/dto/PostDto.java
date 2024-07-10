package org.mylog.domain.post.dto;

import lombok.*;
import org.mylog.domain.comment.dto.CommentDto;
import org.mylog.domain.like.dto.LikeDto;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.series.dto.SeriesDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isTemp;
    private Boolean isPrivate;
    private Boolean isDeleted;
    private String thumbnailUrl;

    private Long userId;
    private Long blogId;

    private SeriesDto series;
    private List<CommentDto> comments;
    private List<LikeDto> likes;
    private List<PostImageDto> postImages;
    private List<PostTagDto> postTags;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.isTemp = post.getIsTemp();
        this.isPrivate = post.getIsPrivate();
        this.isDeleted = post.getIsDeleted();
        this.thumbnailUrl = post.getThumbnailUrl();
        this.userId = post.getUser().getId();
        this.blogId = post.getBlog().getId();
        this.series = post.getSeries() != null ? new SeriesDto(post.getSeries()) : null;
        this.comments = post.getComments().stream().map(CommentDto::new).toList();
        this.likes = post.getLikes().stream().map(LikeDto::new).toList();
        this.postImages = post.getPostImages().stream().map(PostImageDto::new).toList();
        this.postTags = post.getPostTags().stream().map(PostTagDto::new).toList();
    }
}

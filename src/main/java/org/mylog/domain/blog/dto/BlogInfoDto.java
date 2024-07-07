package org.mylog.domain.blog.dto;

import lombok.*;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.series.domain.Series;

import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlogInfoDto {
    private Long id;
    private String title;
    private String intro;
    private String profileImg;
    private String email;
    private String github;
    private Boolean isDeleted;
    private List<Post> postList;
    private List<Series> seriesList;

    private Long userId;
    private String username;
    private String nickname;
}

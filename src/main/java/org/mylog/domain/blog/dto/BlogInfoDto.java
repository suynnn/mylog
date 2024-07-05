package org.mylog.domain.blog.dto;

import lombok.*;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.user.domain.User;

import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlogInfoDto {
    private Long blogId;
    private String title;
    private String intro;
    private String profileImg;
    private String email;
    private String github;
    private Boolean isDeleted;
    private List<Series> seriesList;

    private Long userId;
    private String username;
    private String nickname;
}

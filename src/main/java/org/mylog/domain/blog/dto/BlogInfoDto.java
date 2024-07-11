package org.mylog.domain.blog.dto;

import lombok.*;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostDto;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.dto.SeriesDto;
import org.mylog.domain.user.dto.UserDto;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BlogInfoDto {
    private Long id;
    private String title;
    private String intro;
    private String profileImg;
    private String email;
    private String github;
    private Boolean isDeleted;
    private List<PostDto> postList;
    private List<SeriesDto> seriesList;

    private UserDto user;

    public BlogInfoDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.intro = blog.getIntro();
        this.profileImg = "/upload/" + blog.getProfileImg();
        this.email = blog.getEmail();
        this.github = blog.getGithub();
        this.isDeleted = blog.getIsDeleted();
        this.postList = blog.getPostList().stream().map(PostDto::new).toList();
        this.seriesList = blog.getSeriesList().stream().map(SeriesDto::new).toList();
        this.user = new UserDto(blog.getUser());
    }
}

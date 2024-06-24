package org.mylog.dto.blog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BlogMakeDto {
    private String title;
    private String intro;
    private String profileImg;
    private String email;
    private String github;
    private Long userId;
}

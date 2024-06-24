package org.mylog.dto.blog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
public class BlogMakeDto {
    private String title;
    private String intro;
    private MultipartFile profileImg;
    private String email;
    private String github;
    private Long userId;
}

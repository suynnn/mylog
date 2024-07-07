package org.mylog.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class PostPublishDto {
    private String title;
    private String content;
    private Boolean isTemp;
    private Boolean isPrivate;
    private String thumbnailUrl;
    private Long userId;
    private List<String> series;
    private List<MultipartFile> images;
    private List<String> tags;
}

package org.mylog.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class PostPublishDto {

    @NotBlank(message = "title은 공백을 허용하지 않습니다.")
    @Size(min= 1, max = 200, message = "title은 1~200자 까지만 허용합니다.")
    private String title;

    @NotBlank(message = "content은 공백을 허용하지 않습니다.")
    private String content;

    private Boolean isTemp;
    private Boolean isPrivate;
    private MultipartFile thumbnail;
    private Long userId;
    private Long blogId;
    private Long seriesId;
    private List<MultipartFile> images;
    private List<String> tags;
}

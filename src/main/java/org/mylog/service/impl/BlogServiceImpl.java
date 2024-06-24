package org.mylog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.Blog;
import org.mylog.dto.blog.BlogMakeDto;
import org.mylog.file.FileStore;
import org.mylog.repository.BlogRepository;
import org.mylog.service.BlogService;
import org.mylog.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final FileStore fileStore;

    @Override
    public Long makeBlog(BlogMakeDto dto) {

        String profileImg;

        if (dto.getProfileImg().isEmpty()) {
            profileImg = "basic-profile.png";
        } else {
            profileImg = fileStore.storeFile(dto.getProfileImg()).getStoreFileName();
        }

        Blog blog = Blog.builder()
                .title(dto.getTitle())
                .intro(dto.getIntro())
                .profileImg(profileImg)
                .email(dto.getEmail())
                .github(dto.getGithub())
                .isDeleted(false)
                .user(userService.findUserByUserId(dto.getUserId()))
                .build();

        return blogRepository.save(blog).getBlogId();
    }
}

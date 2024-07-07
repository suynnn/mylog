package org.mylog.domain.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.blog.dto.BlogMakeDto;
import org.mylog.domain.blog.repository.BlogRepository;
import org.mylog.domain.blog.service.BlogService;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.global.file.FileStore;
import org.mylog.domain.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final FileStore fileStore;

    @Override
    public Long makeBlog(BlogMakeDto dto) {

        String profileImg;

        if (dto.getProfileImg() == null) {
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
                .user(userService.findUserByUsername(dto.getUsername()))
                .build();

        return blogRepository.save(blog).getId();
    }

    @Override
    public Optional<Blog> getBlogFindById(Long id) {
        return blogRepository.findById(id);
    }
}

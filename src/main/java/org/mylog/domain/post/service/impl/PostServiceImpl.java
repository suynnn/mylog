package org.mylog.domain.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.repository.PostRepository;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.series.service.SeriesService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final SeriesService seriesService;

    @Override
    public Long publishPost(PostPublishDto postPublishDto) {
        User user = userService.findUserByUserId(postPublishDto.getUserId());

        Post post = Post.builder()
                .title(postPublishDto.getTitle())
                .content(postPublishDto.getContent())
                .createdAt(LocalDateTime.now())
                .isTemp(postPublishDto.getIsTemp())
                .isPrivate(postPublishDto.getIsPrivate())
                .isDeleted(false)

                .build();

        return null;
    }
}

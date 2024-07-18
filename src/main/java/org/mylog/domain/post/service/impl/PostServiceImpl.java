package org.mylog.domain.post.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.repository.PostRepository;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.post.service.PostTagService;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.service.SeriesService;
import org.mylog.domain.tag.domain.Tag;
import org.mylog.domain.tag.service.TagService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.service.UserService;
import org.mylog.global.file.FileStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final SeriesService seriesService;
    private final TagService tagService;
    private final PostTagService postTagService;

    private final FileStore fileStore;

    @Override
    public Post publishPost(PostPublishDto postPublishDto) {
        User user = userService.findUserByUserId(postPublishDto.getUserId());

        Series series = null;
        if (postPublishDto.getSeriesId() != null) {
            series = seriesService.findBySeriesId(postPublishDto.getSeriesId()).orElseThrow();
        }

        String thumbnailImg = null;
        if (!postPublishDto.getThumbnail().isEmpty()) {
            thumbnailImg = fileStore.storeFile(postPublishDto.getThumbnail()).getStoreFileName();
        }

        Post post = Post.builder()
                .title(postPublishDto.getTitle())
                .content(postPublishDto.getContent())
                .createdAt(LocalDateTime.now())
                .isTemp(postPublishDto.getIsTemp())
                .isPrivate(postPublishDto.getIsPrivate())
                .isDeleted(false)
                .thumbnailUrl(thumbnailImg)
                .user(user)
                .blog(user.getBlog())
                .series(series)
                .build();

        Post savedPost = postRepository.save(post);

        for (String tagName : postPublishDto.getTags()) {
            Tag tag;

            if (!tagService.existsTagByName(tagName)) {
                tag = tagService.saveTag(tagName);
            } else {
                tag = tagService.findByTagName(tagName).orElseThrow();
            }

            postTagService.savePostTag(post, tag);
        }

        return savedPost;
    }

    @Override
    public List<Post> findAllPostByDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findTempPostsByUserId(Long userId) {
        return postRepository.findPostsByUserIdAndIsTempAndIsDeletedOrderByCreatedAtDesc(userId, true, false);
    }

    @Override
    public Post updatePost(Long postId, PostPublishDto postPublishDto) {

        Post post = postRepository.findById(postId).orElseThrow();

        Series series = null;
        if (postPublishDto.getSeriesId() != null) {
            series = seriesService.findBySeriesId(postPublishDto.getSeriesId()).orElseThrow();
        }

        String thumbnailImg = null;
        if (!postPublishDto.getThumbnail().isEmpty()) {
            thumbnailImg = fileStore.storeFile(postPublishDto.getThumbnail()).getStoreFileName();
        }

        post.updatePost(postPublishDto.getTitle(), postPublishDto.getContent(), postPublishDto.getIsTemp(), postPublishDto.getIsPrivate(), thumbnailImg, series);

        postTagService.deleteByPostId(postId);

        for (String tagName : postPublishDto.getTags()) {
            log.info("tagName {} ", tagName);
            Tag tag;

            if (!tagService.existsTagByName(tagName)) {
                tag = tagService.saveTag(tagName);
            } else {
                tag = tagService.findByTagName(tagName).orElseThrow();
            }

            postTagService.savePostTag(post, tag);
        }

        return post;
    }
}

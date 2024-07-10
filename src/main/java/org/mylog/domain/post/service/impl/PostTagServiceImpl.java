package org.mylog.domain.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.domain.PostTag;
import org.mylog.domain.post.repository.PostTagRepository;
import org.mylog.domain.post.service.PostTagService;
import org.mylog.domain.tag.domain.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostTagServiceImpl implements PostTagService {

    private final PostTagRepository postTagRepository;

    @Override
    public void savePostTag(Post post, Tag tag) {
        PostTag postTag = PostTag.builder()
                .post(post)
                .tag(tag)
                .build();

        postTagRepository.save(postTag);
    }
}

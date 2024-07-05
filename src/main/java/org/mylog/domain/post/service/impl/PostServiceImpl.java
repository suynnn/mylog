package org.mylog.domain.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.post.repository.PostRepository;
import org.mylog.domain.post.service.PostService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
}

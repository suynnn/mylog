package org.mylog.domain.post.service;

import org.mylog.domain.post.dto.PostPublishDto;

public interface PostService {
    Long publishPost(PostPublishDto postPublishDto);
}

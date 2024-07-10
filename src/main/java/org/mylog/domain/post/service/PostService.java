package org.mylog.domain.post.service;

import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostPublishDto;

public interface PostService {
    Post publishPost(PostPublishDto postPublishDto);
}

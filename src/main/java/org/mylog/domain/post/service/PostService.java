package org.mylog.domain.post.service;

import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostPublishDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post publishPost(PostPublishDto postPublishDto);

    List<Post> findAllPost();

    Optional<Post> findPostById(Long id);

}

package org.mylog.domain.blog.service;

import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.blog.dto.BlogMakeDto;

import java.util.Optional;

public interface BlogService {
    Long makeBlog(BlogMakeDto dto);

    Optional<Blog> getBlogFindById(Long id);
}

package org.mylog.domain.blog.service;

import org.mylog.domain.blog.dto.BlogMakeDto;

public interface BlogService {
    Long makeBlog(BlogMakeDto dto);
}

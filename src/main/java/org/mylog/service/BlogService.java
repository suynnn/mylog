package org.mylog.service;

import org.mylog.dto.blog.BlogMakeDto;

public interface BlogService {
    Long makeBlog(BlogMakeDto dto);
}

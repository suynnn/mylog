package org.mylog.domain.post.service;

import org.mylog.domain.post.domain.Post;
import org.mylog.domain.tag.domain.Tag;

public interface PostTagService {

    void savePostTag(Post post, Tag tag);
}

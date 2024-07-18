package org.mylog.domain.post.repository;

import org.mylog.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    void deletePostTagsByPostId(Long postId);
}

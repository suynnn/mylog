package org.mylog.domain.tag.service;

import org.mylog.domain.tag.domain.Tag;

import java.util.Optional;

public interface TagService {
    Tag saveTag(String name);

    Boolean existsTagByName(String name);

    Optional<Tag> findByTagName(String name);
}

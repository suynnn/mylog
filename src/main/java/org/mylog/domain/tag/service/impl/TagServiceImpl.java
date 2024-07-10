package org.mylog.domain.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.tag.domain.Tag;
import org.mylog.domain.tag.repository.TagRepository;
import org.mylog.domain.tag.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag saveTag(String name) {
        Optional<Tag> tagFindWithName = tagRepository.findByName(name);

        if (tagFindWithName.isPresent()) {
            return tagFindWithName.get();
        }

        Tag tag = Tag.builder()
                .name(name)
                .build();

        return tagRepository.save(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsTagByName(String name) {
        return tagRepository.existsTagByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tag> findByTagName(String name) {
        return tagRepository.findByName(name);
    }
}

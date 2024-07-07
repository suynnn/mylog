package org.mylog.domain.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.tag.repository.TagRepository;
import org.mylog.domain.tag.service.TagService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
}

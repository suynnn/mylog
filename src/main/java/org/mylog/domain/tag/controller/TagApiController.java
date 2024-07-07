package org.mylog.domain.tag.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.tag.service.TagService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagApiController {

    private final TagService tagService;
}

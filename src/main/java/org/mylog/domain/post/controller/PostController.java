package org.mylog.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.post.service.PostService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
}

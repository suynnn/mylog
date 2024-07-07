package org.mylog.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.service.PostService;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/publish")
    public String publishPostForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Model model) {
        PostPublishDto postPublishDto = new PostPublishDto();

        model.addAttribute("postPublishDto", postPublishDto);

        return "post/post-publish-form";
    }
}

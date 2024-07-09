package org.mylog.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.series.dto.SeriesRegisterDto;
import org.mylog.domain.tag.dto.TagRegisterDto;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/publish")
    public String publishPostForm(Model model) {

        model.addAttribute("postPublishDto", new PostPublishDto());
        model.addAttribute("seriesRegisterDto", new SeriesRegisterDto());
        model.addAttribute("tagRegisterDto", new TagRegisterDto());

        return "post/post-publish-form";
    }

    @PostMapping("/publish")
    public String publishPost(@Valid @ModelAttribute("postPublishDto") PostPublishDto postPublishDto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "post/post-publish-form";
        }

        log.info("postPublishDto {}", postPublishDto);

        return "redirect:/";
    }
}

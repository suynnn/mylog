package org.mylog.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostDto;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.series.dto.SeriesRegisterDto;
import org.mylog.domain.tag.dto.TagRegisterDto;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                              BindingResult bindingResult,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            return "post/post-publish-form";
        }

        log.info("postPublishDto {}", postPublishDto);

        Long postId = postService.publishPost(postPublishDto).getId();

        return "redirect:/posts/@" + userDetails.getUsername() + "/" + postId;
    }

    @GetMapping("/@{username}/{id}")
    public String showPost(@PathVariable("username") String username,
                           @PathVariable("id") Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {

        Post post = postService.findPostById(id).orElseThrow();

        if (userDetails == null
                || !username.equals(userDetails.getUsername())
                || !userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {

            if (post.getIsPrivate() || post.getIsTemp()) {
                return "redirect:/";
            }
        }

        model.addAttribute("postDto", new PostDto(post));

        return "post/post";
    }

}

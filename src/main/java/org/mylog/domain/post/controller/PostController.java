package org.mylog.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.comment.dto.CommentRegisterDto;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.post.dto.PostDto;
import org.mylog.domain.post.dto.PostPublishDto;
import org.mylog.domain.post.service.PostService;
import org.mylog.domain.series.dto.SeriesRegisterDto;
import org.mylog.domain.tag.dto.TagRegisterDto;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        Long postId = postService.publishPost(postPublishDto).getId();

        return "redirect:/posts/@" + userDetails.getUsername() + "/" + postId;
    }

    @GetMapping("/@{username}/{id}")
    public String showPost(@PathVariable("username") String username,
                           @PathVariable("id") Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {

        Post post = postService.findPostById(id).orElseThrow();

        if (userDetails == null
                || !username.equals(userDetails.getUsername())
                || !userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {

            if (post.getIsPrivate() || post.getIsTemp()) {
                if (userDetails.getUserId() != post.getUser().getId()) {
                    return "redirect:/";
                }
            }
        }

        model.addAttribute("postDto", new PostDto(post));
        model.addAttribute("commentRegisterDto", new CommentRegisterDto());

        return "post/post";
    }

    @GetMapping("/temps")
    public String tempPostList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               Model model) {

        List<Post> posts = postService.findTempPostsByUserId(customUserDetails.getUserId());

        List<PostDto> postDtoList = posts.stream()
                .map(PostDto::new).toList();

        model.addAttribute("postDtoList", postDtoList);

        return "post/post-temp-list";
    }

    @GetMapping("/update/{postId}")
    public String PostUpdateForm(@PathVariable("postId") Long postId,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model) {
        Post post = postService.findPostById(postId).orElseThrow();

        if (customUserDetails.getUserId() != post.getUser().getId()) {
            return "redirect:/";
        }

        PostPublishDto postPublishDto = new PostPublishDto();
        postPublishDto.setTitle(post.getTitle());
        postPublishDto.setContent(post.getContent());
        postPublishDto.setTags(post.getPostTags().stream().map(postTag -> postTag.getTag().getName()).toList());

        model.addAttribute("postPublishDto", postPublishDto);
        model.addAttribute("postId", postId);

        return "post/post-update-form";
    }

    @PostMapping("/update/{postId}")
    public String postUpdate(@Valid @ModelAttribute("postPublishDto") PostPublishDto postPublishDto,
                             BindingResult bindingResult,
                             @PathVariable("postId") Long postId) {

        if (bindingResult.hasErrors()) {
            return "post/post-publish-form";
        }

        Post post = postService.updatePost(postId, postPublishDto);

        return "redirect:/posts/@" + post.getUser().getUsername() + "/" + postId;
    }

}

package org.mylog.domain.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.blog.dto.BlogInfoDto;
import org.mylog.domain.blog.dto.BlogMakeDto;
import org.mylog.domain.blog.service.BlogService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/register")
    public String registerBlogForm(Model model,
                                   HttpServletRequest request) {

        model.addAttribute("blogMakeDto", new BlogMakeDto());

        return "blog/blog-register-form";
    }

    @PostMapping("/register")
    public String registerBlog(@Valid @ModelAttribute("blogMakeDto") BlogMakeDto blogMakeDto,
                               BindingResult bindingResult,
                               HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "blog/blog-register-form";
        }

        log.info("blogMakeDto : {}", blogMakeDto);

        blogService.makeBlog(blogMakeDto);

        return "redirect:/blogs/@" + blogMakeDto.getUsername();
    }

    @GetMapping("/@{username}")
    public String showBlog(@PathVariable("username") String username,
                           Model model) {

        User user = userService.findUserByUsername(username);

        if (user.getBlog() == null) {
            return "redirect:/blogs/register";
        }

        Blog blog = blogService.getBlogFindById(user.getBlog().getId()).orElseThrow();

        BlogInfoDto blogInfoDto = BlogInfoDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .intro(blog.getIntro())
                .profileImg(uploadPath+blog.getProfileImg())
                .email(blog.getEmail())
                .github(blog.getGithub())
                .isDeleted(blog.getIsDeleted())
                .postList(blog.getPostList())
                .seriesList(blog.getSeriesList())
                .userId(blog.getUser().getId())
                .username(blog.getUser().getUsername())
                .nickname(blog.getUser().getNickname())
                .build();

        model.addAttribute("blogInfoDto", blogInfoDto);

        return "blog/my-blog";
    }
}

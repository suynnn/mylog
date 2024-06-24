package org.mylog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.User;
import org.mylog.dto.blog.BlogMakeDto;
import org.mylog.etc.ConstValues;
import org.mylog.service.BlogService;
import org.mylog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final UserService userService;

    @GetMapping("/register")
    public String registerBlogForm(Model model,
                                   HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/";
        }

        Long userId = (Long) session.getAttribute(ConstValues.SESSION_LOGIN_USER);
        User user = userService.findUserByUserId(userId);

        if (user == null || user.getBlog() != null) {
            return "redirect:/";
        }

        model.addAttribute("blogMakeDto", new BlogMakeDto());

        return "blog/blog-register-form";
    }

    @PostMapping("/register")
    public String registerBlog(@Valid @ModelAttribute("blogMakeDto") BlogMakeDto blogMakeDto,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "blog/blog-register-form";
        }

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/";
        }

        Long userId = (Long) session.getAttribute(ConstValues.SESSION_LOGIN_USER);

        blogMakeDto.setUserId(userId);
        Long blogId = blogService.makeBlog(blogMakeDto);

        redirectAttributes.addAttribute("blogId", blogId);

        return "redirect:/blogs/{blogId}";
    }

    @GetMapping("/{blogId}")
    public String showBlog(@PathVariable("blogId") Long blogId) {

        return "blog/my-blog";
    }
}

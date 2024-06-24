package org.mylog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.dto.blog.BlogMakeDto;
import org.mylog.dto.user.LoginUserContext;
import org.mylog.etc.ConstValues;
import org.mylog.service.BlogService;
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

    @GetMapping("/register")
    public String registerBlogForm(Model model,
                                   HttpServletRequest request) {
        LoginUserContext userContext = getLoginUserContext(request);

        if (userContext == null) return "redirect:/";

        if (userContext.getBlog() != null) {
            return "redirect:/";
        }

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

        LoginUserContext userContext = getLoginUserContext(request);

        if (userContext == null) return "redirect:/";

        blogMakeDto.setUserId(userContext.getUserId());
        blogService.makeBlog(blogMakeDto);

        return "redirect:/blogs/@" + userContext.getId();
    }

    @GetMapping("/@{id}")
    public String showBlog(@PathVariable("id") String id,
                           HttpServletRequest request) {
        LoginUserContext userContext = getLoginUserContext(request);

        if (userContext == null) return "redirect:/";

        return "blog/my-blog";
    }

    private LoginUserContext getLoginUserContext(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        LoginUserContext loginUser = (LoginUserContext) session.getAttribute(ConstValues.SESSION_LOGIN_USER);

        return loginUser;
    }
}

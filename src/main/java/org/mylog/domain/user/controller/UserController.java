package org.mylog.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.blog.dto.BlogMakeDto;
import org.mylog.domain.blog.service.BlogService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.dto.UserRegisterDto;
import org.mylog.domain.user.service.LoginService;
import org.mylog.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BlogService blogService;

    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());

        return "user/user-register-form";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto,
                               BindingResult bindingResult,
                               HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "user/user-register-form";
        }

        User user = userService.registerUser(userRegisterDto);

        BlogMakeDto blogMakeDto = new BlogMakeDto();
        blogMakeDto.setTitle(user.getNickname()+"의 블로그");
        blogMakeDto.setUsername(user.getUsername());

        blogService.makeBlog(blogMakeDto);

        return "redirect:/login-form";
    }
}

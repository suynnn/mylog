package org.mylog.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.blog.dto.BlogMakeDto;
import org.mylog.domain.blog.service.BlogService;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.dto.UserRegisterDto;
import org.mylog.domain.user.dto.UserUpdateDto;
import org.mylog.domain.user.service.LoginService;
import org.mylog.domain.user.service.UserService;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.mylog.global.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());

        return "user/user-register-form";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto,
                               BindingResult bindingResult) {

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

    @GetMapping("/update")
    public String updateUserForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 Model model) {

        User user = userService.findUserByUserId(userDetails.getUserId());

        UserUpdateDto userUpdateDto = new UserUpdateDto(user);

        model.addAttribute("userUpdateDto", userUpdateDto);

        return "user/user-update-form";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto) {

        userService.updateUser(userUpdateDto);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String withdrawUserForm() {

        return "user/user-withdraw-form";
    }

    @PostMapping("/delete")
    public String withdrawUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        userService.withdrawUser(customUserDetails.getUserId());

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                if (refreshTokenService.findRefreshToken(cookie.getValue()).isPresent()) {
                    refreshTokenService.deleteRefreshToken(cookie.getValue());
                }

            }
        }
        Cookie accessTokenCookie = new Cookie("accessToken", "");
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/";
    }
}

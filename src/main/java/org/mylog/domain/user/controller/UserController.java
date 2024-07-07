package org.mylog.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final LoginService loginService;

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

        List<String> tokens = loginService.login(user);
        String accessToken = tokens.get(0);
        String refreshToken = tokens.get(1);

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true); // 보안 (쿠키값을 자바스크립트 같은 곳에서는 접근 불가)
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/blogs/register";
    }
}

package org.mylog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.User;
import org.mylog.dto.LoginDto;
import org.mylog.etc.ConstValues;
import org.mylog.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {

        model.addAttribute("loginDto", new LoginDto());

        return "login/login-form";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/", name = "redirectURL") String redirectURL,
                        HttpServletRequest request) {

        User loginUser = loginService.login(loginDto.getId(), loginDto.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/login-form";
        }

        HttpSession session = request.getSession();
        session.setAttribute(ConstValues.SESSION_LOGIN_USER, loginUser.getUserId());

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}

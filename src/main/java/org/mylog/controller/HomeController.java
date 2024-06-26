package org.mylog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.dto.user.LoginUserContext;
import org.mylog.etc.ConstValues;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String homeLogin(HttpServletRequest request,
                            Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "index";
        }

        LoginUserContext loginUser = (LoginUserContext) session.getAttribute(ConstValues.SESSION_LOGIN_USER);

        if (loginUser == null) {
            return "index";
        }

        boolean hasBlog;
        if (loginUser.getBlog() == null) {
            hasBlog = false;
        } else {
            hasBlog = true;
        }

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("hasBlog", hasBlog);

        return "index";
    }
}

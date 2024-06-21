package org.mylog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.User;
import org.mylog.etc.ConstValues;
import org.mylog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @GetMapping
    public String homeLogin(HttpServletRequest request,
                            Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "index";
        }

        Long userId = (Long) session.getAttribute(ConstValues.SESSION_LOGIN_USER);

        User loginUser = userService.findUserByUserId(userId);

        if (loginUser == null) {
            return "index";
        }

        model.addAttribute("loginUser", loginUser);
        return "index";
    }
}

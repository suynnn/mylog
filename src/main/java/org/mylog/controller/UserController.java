package org.mylog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.dto.UserRegisterDto;
import org.mylog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());

        return "user/user-register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto) {

        log.info("userRegisterDto : {}", userRegisterDto);

        userService.registerUser(userRegisterDto);

        return "redirect:/";
    }
}

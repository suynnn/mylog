package org.mylog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.dto.user.UserRegisterDto;
import org.mylog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

        userService.registerUser(userRegisterDto);

        return "redirect:/";
    }
}

package org.mylog.domain.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.dto.LoginDto;
import org.mylog.domain.user.dto.UserLoginResponseDto;
import org.mylog.domain.user.service.UserService;
import org.mylog.global.jwt.domain.RefreshToken;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.mylog.global.jwt.util.JwtTokenizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/login-form")
    public String loginForm() {
        return "login/login-form";
    }
    @PostMapping("/login")
    public String login(LoginDto loginDto,
                        BindingResult bindingResult,
                        HttpServletResponse response) {

        User user = userService.findUserByUsername(loginDto.getUsername());

        if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {

            bindingResult.rejectValue("password", null, "아이디/비밀번호가 올바르지 않습니다.");
            return "login/login-form";
        }

        List<String> roles = user.getUserRoles()
                .stream().map(role -> role.getRole().getRoleEnum().name()).toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getId(),
                roles
        );

        String refreshToken = jwtTokenizer.createRefreshToken(user.getUserId(), user.getEmail());

        // 리프레시 토큰을 디비에 저장
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getUserId());

        refreshTokenService.addRefreshToken(refreshTokenEntity);

        UserLoginResponseDto loginResponseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .name(user.getName())
                .build();

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true); // 보안 (쿠키값을 자바스크립트 같은 곳에서는 접근 불가)
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

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

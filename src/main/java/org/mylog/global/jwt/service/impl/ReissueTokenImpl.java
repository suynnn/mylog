package org.mylog.global.jwt.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.repository.UserRepository;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.mylog.global.jwt.service.ReissueToken;
import org.mylog.global.jwt.util.JwtTokenizer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReissueTokenImpl implements ReissueToken {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;

    // accessToken이 만료되면 refreshToken으로 다시 accessToken을 재발급하는 메서드
    public String reissueAccessToken(HttpServletResponse response,
                                     String refreshToken) {

        if (!verifyRefreshToken(refreshToken) || jwtTokenizer.isRefreshTokenExpired(refreshToken)) {

            Cookie cookie = new Cookie("refreshToken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");

            Cookie accessCookie = new Cookie("accessToken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");

            response.addCookie(cookie);
            response.addCookie(accessCookie);

            if (refreshTokenService.findRefreshToken(refreshToken).isPresent()) {
                refreshTokenService.deleteRefreshToken(refreshToken);
            }

            return "";
        }

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        User user = userRepository.findById(claims.get("userId", Long.class)).orElse(null);

        String accessToken = jwtTokenizer.createAccessToken(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getId(),
                user.getUserRoles()
                        .stream().map(role -> role.getRole().getRoleEnum().name()).toList()
        );

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);

        return accessToken;
    }

    // refreshToken이 유효한지 검증하는 메서드
    private boolean verifyRefreshToken(String refreshToken) {

        if (refreshToken == null) {
            return false;
        }
        else if (refreshTokenService.findRefreshToken(refreshToken).isEmpty()) {
            return false;
        }

        return true;
    }
}

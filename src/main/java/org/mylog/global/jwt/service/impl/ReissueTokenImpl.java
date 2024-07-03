package org.mylog.global.jwt.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.global.jwt.service.BlacklistService;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.mylog.global.jwt.service.ReissueToken;
import org.mylog.global.jwt.util.JwtTokenizer;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReissueTokenImpl implements ReissueToken {

    private final RefreshTokenService refreshTokenService;
    private final BlacklistService blacklistService;
    private final JwtTokenizer jwtTokenizer;

    // accessToken이 만료되면 refreshToken으로 다시 accessToken을 재발급하는 메서드
    public String reissueAccessToken(HttpServletResponse response,
                                     String refreshToken) {

        if (!verifyRefreshToken(refreshToken)) {

            Cookie cookie = new Cookie("refreshToken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");

            Cookie accessCookie = new Cookie("accessToken", "");
            cookie.setMaxAge(0);
            cookie.setPath("/");

            response.addCookie(cookie);
            response.addCookie(accessCookie);

            return "fail";
        }

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        String accessToken = jwtTokenizer.createAccessToken(
                claims.get("userId", Long.class),
                claims.get("email", String.class),
                claims.get("name", String.class),
                claims.get("username", String.class),
                claims.get("roles", java.util.List.class)
        );

        log.info("새로 생성된 accessToken : {}", accessToken);

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
        } else if(blacklistService.findBlacklistByRefreshToken(refreshToken).isPresent()) {
            return false;
        }

        return true;
    }
}

package org.mylog.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.user.service.LoginService;
import org.mylog.domain.user.domain.User;
import org.mylog.global.jwt.domain.RefreshToken;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.mylog.global.jwt.util.JwtTokenizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {


    private final JwtTokenizer jwtTokenizer;

    private final RefreshTokenService refreshTokenService;

    @Override
    public List<String> login(User user) {
        List<String> roles = user.getUserRoles()
                .stream().map(role -> role.getRole().getRoleEnum().name()).toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getUsername(),
                roles
        );

        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail());

        // 리프레시 토큰을 디비에 저장
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());

        refreshTokenService.addRefreshToken(refreshTokenEntity);

        return List.of(accessToken, refreshToken);

    }
}

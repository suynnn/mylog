package org.mylog.global.jwt.service;

import org.mylog.global.jwt.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    public RefreshToken addRefreshToken(RefreshToken refreshToken);

    public Optional<RefreshToken> findRefreshToken(String refreshToken);

    public void deleteRefreshToken(String refreshToken);
}

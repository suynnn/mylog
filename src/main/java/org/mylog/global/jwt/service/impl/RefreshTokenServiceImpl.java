package org.mylog.global.jwt.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.global.jwt.domain.RefreshToken;
import org.mylog.global.jwt.repository.RefreshTokenRepository;
import org.mylog.global.jwt.service.RefreshTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Override
    public RefreshToken addRefreshToken(RefreshToken refreshToken) {
        return repository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<RefreshToken> findRefreshToken(String refreshToken) {
        return repository.findByValue(refreshToken);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        repository.findByValue(refreshToken).ifPresent(repository::delete);
    }
}

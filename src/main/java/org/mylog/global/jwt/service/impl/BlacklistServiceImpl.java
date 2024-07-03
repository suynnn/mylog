package org.mylog.global.jwt.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.global.jwt.domain.Blacklist;
import org.mylog.global.jwt.repository.BlacklistRepository;
import org.mylog.global.jwt.service.BlacklistService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Override
    public Optional<Blacklist> findBlacklistByRefreshToken(String refreshToken) {
        return blacklistRepository.findByInvalidRefreshToken(refreshToken);
    }
}

package org.mylog.global.jwt.service;

import org.mylog.global.jwt.domain.Blacklist;

import java.util.Optional;

public interface BlacklistService {

    public Optional<Blacklist> findBlacklistByRefreshToken(String refreshToken);
}

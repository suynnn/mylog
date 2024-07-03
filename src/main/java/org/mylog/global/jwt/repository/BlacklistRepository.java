package org.mylog.global.jwt.repository;

import org.mylog.global.jwt.domain.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    Optional<Blacklist> findByInvalidRefreshToken(String invalidRefreshToken);
}

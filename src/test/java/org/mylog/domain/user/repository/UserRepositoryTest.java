package org.mylog.domain.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mylog.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeAll() {
        user = User.builder()
                .username("user1")
                .password("1234")
                .name("유저")
                .email("user@test.com")
                .nickname("user_nick")
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false).build();
    }

    @Test
    void findByUsername() {
        userRepository.save(user);

        User user1 = userRepository.findByUsername("user1").orElseThrow();

        System.out.println(user1.getEmail());

    }
}
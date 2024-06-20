package org.mylog.repository;

import org.junit.jupiter.api.Test;
import org.mylog.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        User user = User.builder()
                .id("user1")
                .password("1234")
                .name("유저")
                .email("user@test.com")
                .nickname("user_nick")
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false).build();

        userRepository.save(user);

        User user1 = userRepository.findById(1L).orElseThrow();

        System.out.println(user1.getUserId());
    }

}
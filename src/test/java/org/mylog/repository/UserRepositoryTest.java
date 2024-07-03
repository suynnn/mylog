package org.mylog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeAll() {
        user = User.builder()
                .id("user1")
                .password("1234")
                .name("유저")
                .email("user@test.com")
                .nickname("user_nick")
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false).build();
    }

    @Test
    void saveUser() {
        userRepository.save(user);

        User user1 = userRepository.findById(1L).orElseThrow();

        System.out.println(user1.getUserId());
    }

    @Test
    void findById() {
        userRepository.save(user);

        User user1 = userRepository.findById("user1").orElseThrow();

        System.out.println(user1.getEmail());

    }

}
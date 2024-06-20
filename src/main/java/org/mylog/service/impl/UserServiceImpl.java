package org.mylog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.User;
import org.mylog.dto.UserResisterDto;
import org.mylog.repository.UserRepository;
import org.mylog.service.UserRoleService;
import org.mylog.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long registerUser(UserResisterDto dto) {
        User user = User.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false)
                .userRoles(userRoleService.getUserRoles())
                .build();

        return userRepository.save(user).getUserId();

    }

}

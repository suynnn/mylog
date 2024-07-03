package org.mylog.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.user.domain.Role;
import org.mylog.domain.user.domain.UserRole;
import org.mylog.domain.user.dto.UserRegisterDto;
import org.mylog.domain.user.repository.UserRepository;
import org.mylog.domain.user.service.RoleService;
import org.mylog.domain.user.service.UserRoleService;
import org.mylog.domain.user.service.UserService;
import org.mylog.domain.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long registerUser(UserRegisterDto dto) {
        User user = User.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false)
                .build();

        Long id = userRepository.save(user).getUserId();

        Role role = roleService.findRoleById(2L);

        UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(role)
                        .build();

        userRoleService.registerUserRole(userRole);

        return id;

    }

    @Override
    public User findUserByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByUsername(String username) {

        return userRepository.findById(username).orElse(null);
    }
}

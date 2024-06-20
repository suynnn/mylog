package org.mylog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.Role;
import org.mylog.domain.User;
import org.mylog.domain.UserRole;
import org.mylog.dto.UserRegisterDto;
import org.mylog.repository.UserRepository;
import org.mylog.service.RoleService;
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

        Role role = roleService.findRoleById(1L);

        UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(role)
                        .build();

        userRoleService.registerUserRole(userRole);

        return id;

    }

}

package org.mylog.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mylog.domain.user.domain.Role;
import org.mylog.domain.user.domain.UserRole;
import org.mylog.domain.user.dto.UserRegisterDto;
import org.mylog.domain.user.dto.UserUpdateDto;
import org.mylog.domain.user.repository.UserRepository;
import org.mylog.domain.user.service.RoleService;
import org.mylog.domain.user.service.UserRoleService;
import org.mylog.domain.user.service.UserService;
import org.mylog.domain.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegisterDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .createdAt(LocalDateTime.now())
                .isWithdrawal(false)
                .build();

        User savedUser = userRepository.save(user);

        Role role = roleService.findRoleById(1L);

        UserRole userRole = UserRole.builder()
                        .user(user)
                        .role(role)
                        .build();

        userRoleService.registerUserRole(userRole);

        user.setUserRoles(List.of(userRole));

        return savedUser;

    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Long updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId()).orElseThrow();

        String password = null;
        if (!"".equals(userUpdateDto.getPassword())) {
            password = passwordEncoder.encode(userUpdateDto.getPassword());
        }

        user.updateUserInfo(password, userUpdateDto.getEmail(), userUpdateDto.getNickname());

        return user.getId();
    }

    @Override
    public void withdrawUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        user.deleteUser();
    }

    @Override
    public Boolean existsUserId(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public Boolean existsEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }
}

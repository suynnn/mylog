package org.mylog.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.user.domain.UserRole;
import org.mylog.domain.user.repository.UserRoleRepository;
import org.mylog.domain.user.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public void registerUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> getUserRoles() {
        return userRoleRepository.findAll();
    }
}

package org.mylog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.Role;
import org.mylog.domain.UserRole;
import org.mylog.repository.UserRoleRepository;
import org.mylog.service.UserRoleService;
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

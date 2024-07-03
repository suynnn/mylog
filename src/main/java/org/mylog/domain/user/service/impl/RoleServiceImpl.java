package org.mylog.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.user.domain.Role;
import org.mylog.domain.user.repository.RoleRepository;
import org.mylog.domain.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }
}

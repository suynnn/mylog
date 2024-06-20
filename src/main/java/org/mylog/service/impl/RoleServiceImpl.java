package org.mylog.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.Role;
import org.mylog.repository.RoleRepository;
import org.mylog.service.RoleService;
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

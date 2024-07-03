package org.mylog.domain.user.service;

import org.mylog.domain.user.domain.UserRole;

import java.util.List;

public interface UserRoleService {

    void registerUserRole(UserRole userRole);

    List<UserRole> getUserRoles();
}

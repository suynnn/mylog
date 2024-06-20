package org.mylog.service;

import org.mylog.domain.Role;
import org.mylog.domain.UserRole;

import java.util.List;

public interface UserRoleService {

    void registerUserRole(UserRole userRole);

    List<UserRole> getUserRoles();
}

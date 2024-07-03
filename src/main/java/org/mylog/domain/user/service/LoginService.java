package org.mylog.domain.user.service;

import org.mylog.domain.user.domain.User;

public interface LoginService {

    public User login(String id, String password);
}

package org.mylog.service;

import org.mylog.domain.User;

public interface LoginService {

    public User login(String id, String password);
}

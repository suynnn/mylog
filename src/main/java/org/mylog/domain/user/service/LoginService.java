package org.mylog.domain.user.service;

import org.mylog.domain.user.domain.User;

import java.util.List;

public interface LoginService {

    public List<String> login(User user);
}

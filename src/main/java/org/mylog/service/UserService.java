package org.mylog.service;

import org.mylog.domain.User;
import org.mylog.dto.user.UserRegisterDto;

public interface UserService {

    Long registerUser(UserRegisterDto dto);

    User findUserByUserId(Long id);
}

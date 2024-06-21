package org.mylog.service;

import org.mylog.domain.User;
import org.mylog.dto.UserRegisterDto;

public interface UserService {

    Long registerUser(UserRegisterDto dto);

    User findUserByUserId(Long id);
}

package org.mylog.domain.user.service;

import org.mylog.domain.user.domain.User;
import org.mylog.domain.user.dto.UserRegisterDto;
import org.mylog.domain.user.dto.UserUpdateDto;

public interface UserService {

    User registerUser(UserRegisterDto dto);

    User findUserByUserId(Long id);

    User findUserByUsername(String username);

    Long updateUser(UserUpdateDto userUpdateDto);
}

package org.mylog.service;

import org.mylog.dto.UserRegisterDto;

public interface UserService {

    Long registerUser(UserRegisterDto dto);
}

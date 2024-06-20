package org.mylog.service;

import org.mylog.dto.UserResisterDto;

public interface UserService {

    Long registerUser(UserResisterDto dto);
}

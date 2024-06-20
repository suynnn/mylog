package org.mylog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDto {

    private String id;
    private String password;
    private String name;
    private String email;
    private String nickname;

}

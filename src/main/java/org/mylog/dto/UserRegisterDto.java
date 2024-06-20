package org.mylog.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank(message = "id는 공백을 허용하지 않습니다.")
    @Size(min= 4, max = 30, message = "id는 4~30자 까지만 허용합니다.")
    private String id;

    @NotBlank(message = "password는 공백을 허용하지 않습니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "특수문자(@#$%^&+=!), 영문자, 숫자를 포함한 비밀번호를 입력하세요")
    @Size(min= 4, max = 30, message = "password는 4~30자 까지만 허용합니다.")
    private String password;

    @NotBlank(message = "name는 공백을 허용하지 않습니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력 가능합니다")
    @Size(min= 2, max = 10, message = "name은 2~10자 까지만 허용합니다.")
    private String name;

    @NotBlank(message = "email는 공백을 허용하지 않습니다.")
    @Email(message = "이메일 형식만 입력 가능합니다.")
    @Size(min= 1, max = 50, message = "email은 1~50자 까지만 허용합니다.")
    private String email;

    @NotBlank(message = "nickname는 공백을 허용하지 않습니다.")
    @Size(min= 1, max = 20, message = "nickname은 1~20자 까지만 허용합니다.")
    private String nickname;

}

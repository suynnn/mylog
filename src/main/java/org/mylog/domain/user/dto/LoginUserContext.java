package org.mylog.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mylog.domain.user.domain.Role;

import java.util.List;

@Builder
@Getter
@Setter
public class LoginUserContext {
    private Long userId;
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}

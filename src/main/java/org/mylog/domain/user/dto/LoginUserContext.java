package org.mylog.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.user.domain.UserRole;

import java.util.List;

@Builder
@Getter
@Setter
public class LoginUserContext {
    private Long userId;
    private String id;
    private String name;
    private String email;
    private String nickname;
    private Blog blog;
    private List<UserRole> userRoles;
}

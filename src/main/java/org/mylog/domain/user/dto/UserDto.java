package org.mylog.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private Boolean isWithdrawal;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
        this.isWithdrawal = user.getIsWithdrawal();
    }
}

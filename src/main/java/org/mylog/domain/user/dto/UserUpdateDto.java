package org.mylog.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mylog.domain.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private Boolean isWithdrawal;

    public UserUpdateDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = null;
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
        this.isWithdrawal = user.getIsWithdrawal();
    }
}

package org.mylog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long userId;

    @Column(nullable = false, length = 30, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @CreatedDate // 엔티티가 생성되어 저장될 때 자동으로 시간 저장
    @Column(nullable = false, updatable = false, name = "created_at") // updatable = false : column 수정 시 값 변경을 막는다
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "is_withdrawal")
    private Boolean isWithdrawal;
}

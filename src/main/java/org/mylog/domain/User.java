package org.mylog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(nullable = false, updatable = false, name = "created_at") // updatable = false : column 수정 시 값 변경을 막는다
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "is_withdrawal")
    private Boolean isWithdrawal;

    @OneToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();
}

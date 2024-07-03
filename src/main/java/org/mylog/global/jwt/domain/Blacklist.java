package org.mylog.global.jwt.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@Table(name = "blacklist")
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invalid_refresh_token")
    private String invalidRefreshToken;
}

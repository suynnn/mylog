package org.mylog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mylog.etc.RoleEnum;

@Getter
@Builder
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_enum", nullable = false)
    private RoleEnum roleEnum;
}

package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles", schema = "audit", indexes = {
        @Index(name = "idx_user_roles_role", columnList = "role_id"),
        @Index(name = "idx_user_roles_user", columnList = "usuario_id")
})
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Falta resolver esse problema de id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_ur_user"), nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_ur_role"), nullable = false)
    private Role role;

    @JoinColumn(name = "granted_at")
    @CreationTimestamp
    private LocalDateTime grantedAt;
}

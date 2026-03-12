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
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_ur_user"), nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_ur_role"), nullable = false, insertable = false, updatable = false)
    private Role role;

    @Column(name = "granted_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime grantedAt;
}

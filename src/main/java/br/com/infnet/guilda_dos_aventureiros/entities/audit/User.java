package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios", schema = "audit", indexes = {
        @Index(name = "idx_usuarios_email", columnList = "email"),
        @Index(name = "idx_usuarios_org", columnList = "organizacao_id")
        },
        uniqueConstraints = {
        @UniqueConstraint(name = "uq_usuarios_email_por_org", columnNames = {"email", "organizacao_id"})
        })
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id")
    @SequenceGenerator(schema = "audit", name = "usuarios_id", sequenceName = "usuarios_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizacao_id", foreignKey = @ForeignKey (name = "fk_usuarios_org"), nullable = false)
    private Organization organizationUser;

    @Column(name="nome", nullable = false, length = 120)
    private String name;

    @Column(name="email", nullable = false, unique = true, length = 180)
    private String email;

    @Column(name="senha_hash", nullable = false)
    private String hashPassword;

    @Max(30)
    @Column(name="status", length = 30, nullable = false)
    private String status;

    @Column(name="ultimo_login_em")
    private LocalDateTime lastLoginAt;

    @Column (name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "actorUser", cascade = CascadeType.ALL)
    private List<AuditEntry> auditEntries = new ArrayList<>();
}

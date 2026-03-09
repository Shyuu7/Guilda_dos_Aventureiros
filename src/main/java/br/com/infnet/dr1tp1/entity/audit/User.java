package br.com.infnet.dr1tp1.entity.audit;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios", schema = "audit")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id")
    @SequenceGenerator(schema = "audit", name = "usuarios_id", sequenceName = "usuarios_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizacoes_id")
    private Organization organization;

    @Column(name="nome", nullable = false, length = 120)
    private String name;

    @Column(name="email", nullable = false, unique = true, length = 180)
    private String email;

    @Column(name="senha_hash", nullable = false)
    private String hashPassword;

    @Max(30)
    @Column(name="status", length = 30)
    private String status;

    @Column(name="ultimo_login_em")
    private LocalDateTime lastLoginAt;

    @Column (name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

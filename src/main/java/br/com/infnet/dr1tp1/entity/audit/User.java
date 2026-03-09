package br.com.infnet.dr1tp1.entity.audit;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios", schema = "audit")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organization_id")
    private Organization organization;

    @Column(name="nome")
    @Max(120)
    private String name;

    @Max(180)
    @Column(name="email")
    private String email;

    @Max(255)
    @Column(name="senha_hash")
    private String hashPassword;

    @Max(30)
    @Column(name="status")
    private String status;

    @Column(name="ultimo_login_em")
    private LocalDateTime lastLoginAt;

    @Column (name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}

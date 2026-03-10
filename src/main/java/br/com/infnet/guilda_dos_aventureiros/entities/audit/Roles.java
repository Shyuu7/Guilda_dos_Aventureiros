package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles", schema = "audit")
@Getter
@Setter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id")
    @SequenceGenerator(schema = "audit", name = "roles_id", sequenceName = "roles_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="organizacao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_roles_org"))
    private Organization organization;

    @Column(name="nome", nullable = false, length = 60)
    private String name;

    @Column(name="descricao")
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

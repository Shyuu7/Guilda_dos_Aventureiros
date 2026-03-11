package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles", schema = "audit",
        uniqueConstraints = @UniqueConstraint(name="uq_roles_nome_por_org", columnNames = {"organizacao_id", "nome"}))
@Getter
@Setter
public class Role {
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

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}

package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizacoes", schema = "audit",
        uniqueConstraints = @UniqueConstraint(name = "organizacoes_nome_key", columnNames = "nome"))
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizacao_id")
    @SequenceGenerator(schema = "audit", name = "organizacao_id", sequenceName = "organizacoes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name="nome", nullable = false, length = 120, unique = true)
    private String name;

    @Column(name="ativo", nullable = false)
    private Boolean isActive;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organizationUser", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "organizationEntry", cascade = CascadeType.ALL)
    private List<AuditEntry> auditEntries = new ArrayList<>();
}

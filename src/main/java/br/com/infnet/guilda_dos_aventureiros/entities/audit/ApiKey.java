package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "api_keys", schema = "audit",
        uniqueConstraints = @UniqueConstraint(name = "uq_api_keys_nome_por_org", columnNames = {"organizacao_id", "nome"}))
@Getter
@Setter
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_keys_id")
    @SequenceGenerator(schema = "audit", name = "api_keys_id", sequenceName = "api_keys_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organizacao_id", nullable = false, foreignKey = @ForeignKey(name="fk_api_keys_org"))
    private Organization organization;

    @Column(name="nome", nullable = false, length = 120)
    private String name;

    @Column(name="key_hash", nullable = false, unique = true)
    private String hashKey;

    @Column(name="ativo", nullable = false)
    private Boolean isActive;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="last_used_at")
    private LocalDateTime lastUsedAt;

    @OneToMany(mappedBy = "actorApiKey", cascade = CascadeType.ALL)
    private List<AuditEntry> auditEntries = new ArrayList<>();
}

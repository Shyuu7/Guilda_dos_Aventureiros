package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}

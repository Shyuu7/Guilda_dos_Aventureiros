package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizacao_id")
    @SequenceGenerator(schema = "audit", name = "organizacao_id", sequenceName = "organizacoes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name="nome", nullable = false, length = 120)
    private String name;

    @Column(name="ativo", nullable = false)
    private Boolean isActive;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}

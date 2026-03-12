package br.com.infnet.guilda_dos_aventureiros.entities.audit;

import jakarta.persistence.*;
import jakarta.validation.Valid;

@Entity
@Table(name = "permissions", schema = "audit")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_id")
    @SequenceGenerator(schema = "audit", name = "permissions_id", sequenceName = "permissions_id_seq", allocationSize = 1)
    private Long id;

    @Valid
    @Column(name = "code", length = 80, nullable = false)
    private String code;

    @Column(name = "descricao", nullable = false)
    private String descricao;
}

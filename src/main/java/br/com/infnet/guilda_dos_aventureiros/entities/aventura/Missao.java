package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "missoes", schema = "aventura")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organization organizacao;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoMissao> participacoes;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private NivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusMissao status;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_termino")
    private LocalDateTime dataTermino;
}
